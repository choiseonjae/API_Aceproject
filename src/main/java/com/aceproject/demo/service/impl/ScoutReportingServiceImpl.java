package com.aceproject.demo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aceproject.demo.dao.AccountDao;
import com.aceproject.demo.dao.PersonDao;
import com.aceproject.demo.dao.PlayerDao;
import com.aceproject.demo.dao.TeamDao;
import com.aceproject.demo.dao.TeamPlayerDao;
import com.aceproject.demo.dao.TeamSrSlotDao;
import com.aceproject.demo.exception.AlreadyContractPlayerException;
import com.aceproject.demo.exception.NotEnoughApException;
import com.aceproject.demo.exception.NotEnoughPlayerException;
import com.aceproject.demo.model.Account;
import com.aceproject.demo.model.Person;
import com.aceproject.demo.model.Player;
import com.aceproject.demo.model.ScoutReportOption;
import com.aceproject.demo.model.Team;
import com.aceproject.demo.model.TeamPlayer;
import com.aceproject.demo.model.TeamSrSlot;
import com.aceproject.demo.model.TeamSrSlotView;
import com.aceproject.demo.model.YN;
import com.aceproject.demo.service.ScoutReportingService;

@Service
public class ScoutReportingServiceImpl implements ScoutReportingService {

	@Autowired
	private TeamSrSlotDao teamSrSlotDao;

	@Autowired
	private AccountDao accountDao;

	@Autowired
	private TeamDao teamDao;

	@Autowired
	private PlayerDao playerDao;

	@Autowired
	private PersonDao personDao;

	@Autowired
	private TeamPlayerDao teamPlayerDao;

	private static final int REFRESH_HOURS = 1;

	private static final int INIT_LEVEL = 1;

	private static final int INIT_EXP = 1;

	private static final int CONTRACT_AP = 50;

	private static final int MAX_SLOT_NO = 4;

	private static final int OPTION_DEFAULT_AP = 50;

	private static final int OPTION_ONE_AP = 100;

	private static final int OPTION_TWO_AP = 150;

	private static final int COST_UP = 5;
	
	private static final boolean FREE_REFRESH = false;
	
	private static final boolean AP_REFRESH = false;

	// 영입
	@Override
	@Transactional
	public void contract(int teamId, int slotNo) {

		// 현재 slot 목록 조회
		TeamSrSlot srSlot = teamSrSlotDao.get(teamId, slotNo);

		// 이미 영입한 플레이어라면 예외 처리
		if (srSlot.getContractYN().equals(YN.Y))
			throw new AlreadyContractPlayerException();

		// 해당 slot을 영입 여부를 수정
		srSlot.setContractYN(YN.Y);
		teamSrSlotDao.update(srSlot);

		// 영입 비용만큼 team의 ap를 차감
		deductAp(teamId, CONTRACT_AP);

		// 영입할 player를 현재 team의 teamPlyaer 조회
		TeamPlayer teamPlayer = teamPlayerDao.get(teamId, srSlot.getPlayerId());

		// 보유 하지 않은 teamPlayer -> 추가
		// 보유 중인 teamPlayer -> 레벨 +1 증가
		if (teamPlayer == null) {
			teamPlayer = new TeamPlayer(srSlot.getTeamId(), srSlot.getPlayerId(), INIT_LEVEL, INIT_EXP);
			teamPlayerDao.insert(teamPlayer);
		} else {
			teamPlayer.levelUp();
			teamPlayerDao.update(teamPlayer);
		}

	}

	@Override
	public List<TeamSrSlotView> getSrSlots(int teamId) {
		
		// 마지막 refresh 시간 조회
		DateTime lastRfTime = teamDao.get(teamId).getLastRefreshTime();

		// 현재 slot 목록 조회
		List<TeamSrSlot> srSlots = teamSrSlotDao.list(teamId);

		// 갱신이 필요하거나 slot 목록이 비어있을 경우 무료 refresh
		if (isRefresh(lastRfTime) || srSlots.isEmpty())
			return refresh(teamId, ScoutReportOption.DEFAULT_OPTION, FREE_REFRESH);

		// 현재 상태 반환
		return getTeamSrSlotViews(srSlots);
	}


	@Override
	@Transactional
	public List<TeamSrSlotView> refresh(int teamId, ScoutReportOption option, boolean isApRefresh) {

		// 전체 person,player 정보 조회
		List<Player> players = playerDao.getAll();
		List<Person> persons = personDao.getAll();

		// 원래의 경우 player의 DB가 크기 때문에, 쿼리로 옵션 필터를 하는게 낫지만,
		// 현재 과제의 경우 그렇게 크지 않기 때문에 스트림으로 처리
		// 특정 teamCode 존재 시
		if (option.getTeamCode() != null)
			players = players.stream().filter(p -> (p.getTeamCode().equals(option.getTeamCode())))
					.collect(Collectors.toList());
		// cost up 존재 시
		if (option.isCostUp())
			players = players.stream().filter(p -> (p.getCost() >= COST_UP)).collect(Collectors.toList());

		// persons, players 가 3개 이하 일 경우
		if (players.size() < MAX_SLOT_NO || persons.size() < MAX_SLOT_NO)
			throw new NotEnoughPlayerException();

		// 선택된 사람을 보관할 리스트 생성
		Map<Integer, Person> selectedPersonMap = new HashMap<>();
		Map<Integer, Player> selectedPlayerMap = new HashMap<>();

		// 슬롯수를 채울떄까지 player 선정
		int slotNo = 1;

		// 슬롯형태로 변환
		List<TeamSrSlot> refreshSlots = new ArrayList<>();

		// Max_Slot 크기 만큰 사람을 선택한다.
		while (selectedPlayerMap.size() < MAX_SLOT_NO) {

			// 플레이어 랜덤 선택
			Player player = players.get((int) (Math.random() * players.size()));

			// 중복 person이 아닌 경우에만 선택
			if (!selectedPersonMap.containsKey(player.getPersonId())) {

				// srSlot 에 하나의 slot 추가
				refreshSlots.add(new TeamSrSlot(teamId, slotNo++, player.getPlayerId(), YN.N));

				// slot에 추가된 플레이어 객체 추가
				selectedPlayerMap.put(player.getPlayerId(), player);

				// slot에 추가된 플레이어의 사람 정보 추가
				for (Person p : persons) {
					if (p.getPersonId() == player.getPersonId()) {
						selectedPersonMap.put(player.getPersonId(), p);
						break;
					}
				}

			}
		}

		// 기존 slots 존재 여부 조회후 DB 반영 ( 기존 슬롯이 존재 하지 않으면 빈 List 반
		boolean isEmptySlot = teamSrSlotDao.list(teamId).isEmpty();
		if (isEmptySlot)
			teamSrSlotDao.insert(refreshSlots);
		else
			teamSrSlotDao.update(refreshSlots);

		// apRefresh 즉, 유료 refresh의 경우는 마지막 refresh 갱신 x
		if (!isApRefresh) {
			// 마지막 갱신 시간 갱신
			Team team = teamDao.get(teamId);
			team.setLastRefreshTime(DateTime.now());
			teamDao.update(team);
		}

		// 클라이언트가 필요한 player, person, teamsrSlot 정보를 모아서 반환
		return refreshSlots.stream().map(s -> {
			Player player = selectedPlayerMap.get(s.getPlayerId());
			Person person = selectedPersonMap.get(player.getPersonId());

			return new TeamSrSlotView(s, player, person);
		}).collect(Collectors.toList());

//		// view 형태로 변환후 반환
//		Map<Integer, Player> selectedPlayerMap = selectedPlayers.stream()
//				.collect(Collectors.toMap(p -> p.getPlayerId(), p -> p));
//
//		Map<Integer, Person> selectedPersons = persons.stream()
//				.filter(p -> selectedPersonIdMap.contains(p.getPersonId()))
//				.collect(Collectors.toMap(p -> p.getPersonId(), p -> p));

//		return refreshSlots.stream().map(s -> {
//			Player player = selectedPlayerMap.get(s.getPlayerId());
//			Person person = selectedPersonMap.get(player.getPersonId());
//
//			return new TeamSrSlotView(s, player, person);
//		}).collect(Collectors.toList());

		///////////////////////////////////////////////////////////////////

		// 밑의 코드를 위의 주석 처리 형태로도 사용 가능
		// 결과적으로는 밑의 코드 위의 코드 둘다 아닌 상태임.
//		List<TeamSrSlotView> slotSrViews = new ArrayList<>();
//		refreshSlots.stream().forEach(s -> {
//			Player player = selectedPlayerMap.get(s.getPlayerId());
//			Person person = selectedPersons.get(player.getPersonId());
//
//			slotSrViews.add(new TeamSrSlotView(s, player, person));
//		});

//		return slotSrViews;
	}

	// 유료 refresh
	@Override
	@Transactional
	public List<TeamSrSlotView> apRefresh(int teamId, ScoutReportOption option) {

		// 차감 시 필요한 ap 계산
		int refreshtAp = OPTION_ONE_AP;

		// cost up, 특정 teamCode 필터 -> 150
		// cost up x, 특정 teamCode 필터 x -> 50
		if (option.isCostUp() && option.getTeamCode() != null)
			refreshtAp = OPTION_TWO_AP;
		else if (!option.isCostUp() && option.getTeamCode() == null)
			refreshtAp = OPTION_DEFAULT_AP;

		// ap 차감
		deductAp(teamId, refreshtAp);

		// 새로 고침 ( 유료 새로 고침 )
		return refresh(teamId, option, AP_REFRESH);
	}

	// teamSrSlotView를 생성 후 반환
	private List<TeamSrSlotView> getTeamSrSlotViews(List<TeamSrSlot> srSlots) {

		return srSlots.stream().map(srSlot -> {

			Player player = playerDao.get(srSlot.getPlayerId());

			return new TeamSrSlotView(srSlot, player, personDao.get(player.getPersonId()));

		}).collect(Collectors.toList());
	}

	// refresh 필요 여부 체크
	private boolean isRefresh(DateTime lastRefreshTime) {
		return (Hours.hoursBetween(lastRefreshTime, DateTime.now()).getHours() >= REFRESH_HOURS);
	}

	// ap 차감 및 예외 처리
	private void deductAp(int teamId, int deductAp) {
		
		// account 잔고 조회
		Account account = accountDao.get(teamId);

		// 잔고가 부족하면 예외
		if (deductAp > account.getAp())
			throw new NotEnoughApException();

		// 잔고가 부족하지 않으면 ap 차감
		account.setAp(account.getAp() - deductAp);
		accountDao.update(account);
	}


}
