package com.aceproject.demo.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

	@Override
	public List<TeamSrSlotView> getSrSlots(int teamId) {

		// 현재 slot 목록 조회
		List<TeamSrSlot> srSlots = teamSrSlotDao.list(teamId);

		// 갱신 필요 여부
		if (isRefresh(teamId, srSlots))
			return freeRefresh(teamId, ScoutReportOption.DEFAULT_OPTION);

		// 갱신 없이 client에서 필요한 정보들을 취합 후 반환
		return srSlots.stream().map(srSlot -> {

			Player player = playerDao.get(srSlot.getPlayerId());

			return new TeamSrSlotView(srSlot, player, personDao.get(player.getPersonId()));

		}).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void contract(int teamId, int slotNo) {

		// 현재 slot 목록 조회
		TeamSrSlot srSlot = teamSrSlotDao.get(teamId, slotNo);

		// TODO enum은 == 사용
		// 이미 영입한 플레이어라면 예외 발생
		if (YN.Y == srSlot.getContractYN())
			throw new AlreadyContractPlayerException();

		// 해당 slot의 영입 여부를 Y로 수정 후 DB에 반영
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

	@Transactional
	public List<TeamSrSlotView> refresh(int teamId, ScoutReportOption option) {

		// player 같은 경우는 너무 많아서 쿼리로 필터하는 것이 낫다. 하지만 해당 과제에서는 그냥 전체를 받아서 사용
		// person 같은 경우는 cache에서 가져와서 사용하는 경우가 있어서 그냥 전체 가져와서 필터 하는 것이 낫다.
		List<Player> players = playerDao.getAll().stream().filter(option.getFilter()).collect(Collectors.toList());
		List<Person> persons = personDao.list(players.stream().map(Player::getPersonId).distinct().collect(Collectors.toList()));
		
		// players 가 3개 이하 일 경우 예외 발생
		if (players.size() < MAX_SLOT_NO || persons.size() < MAX_SLOT_NO)
			throw new NotEnoughPlayerException();

		// 선택된 사람을 보관할 리스트 생성
		Map<Integer, Player> selectedPlayerMap = new HashMap<>();

		// 슬롯수를 채울떄까지 player 선정
		int slotNo = 1;

		// 슬롯형태로 변환
		List<TeamSrSlot> selectedSrSlots = new ArrayList<>();

		Set<Integer> personIds = new HashSet<>();

		// Max_Slot 크기 만큰 사람을 선택한다.
		while (selectedPlayerMap.size() < MAX_SLOT_NO) {

			// 플레이어 랜덤 선택
			Player player = players.get((int) (Math.random() * players.size()));

			// 중복 person이 아닌 경우에만 선택
			if (personIds.contains(player.getPersonId()))
				continue;

			// srSlot 에 하나의 slot 추가
			selectedSrSlots.add(new TeamSrSlot(teamId, slotNo++, player.getPlayerId(), YN.N));

			// slot에 추가된 플레이어 객체 추가
			selectedPlayerMap.put(player.getPlayerId(), player);

			// slot에 추가된 플레이어의 사람 정보 추가
			personIds.add(player.getPersonId());

		}

		// slot 사람들 정보 조회
		// 선택된 사람들 정보를
		final Map<Integer, Person> selectedPersonMap = personDao.list(new ArrayList<>(personIds)).stream()
				.collect(Collectors.toMap(p -> p.getPersonId(), p -> p));

		// 기존 slots 존재 여부 조회후 DB 반영 ( 기존 슬롯이 존재 하지 않으면 빈 List 반환 )
		boolean isEmptySlot = teamSrSlotDao.list(teamId).isEmpty();
		if (isEmptySlot)
			teamSrSlotDao.insert(selectedSrSlots);
		else
			teamSrSlotDao.update(selectedSrSlots);

		// 클라이언트가 필요한 player, person, teamsrSlot 정보를 모아서 반환
		return selectedSrSlots.stream().map(s -> {
			Player player = selectedPlayerMap.get(s.getPlayerId());
			Person person = selectedPersonMap.get(player.getPersonId());

			return new TeamSrSlotView(s, player, person);
		}).collect(Collectors.toList());
	}

	// 무료 refresh
	@Override
	@Transactional
	public List<TeamSrSlotView> freeRefresh(int teamId, ScoutReportOption option) {

		// 마지막 갱신 시간 갱신
		Team team = teamDao.get(teamId);
		team.setLastRefreshTime(DateTime.now());
		teamDao.update(team);

		return refresh(teamId, option);
	}

	// 유료 refresh
	@Override
	@Transactional
	public List<TeamSrSlotView> apRefresh(int teamId, ScoutReportOption option) {

		// ap 차감
		deductAp(teamId, option.getAp());

		// 새로 고침 ( 유료 새로 고침 )
		List<TeamSrSlotView> srSlotViews = refresh(teamId, option);

		return srSlotViews;
	}

	// 갱신 필요 여부 체크
	private boolean isRefresh(int teamId, List<TeamSrSlot> srSlots) {

		// slot 목록이 비어있으면 갱신 필요
		if (srSlots.isEmpty())
			return true;

		// 마지막 갱신 시간 조회
		DateTime lastRfTime = teamDao.get(teamId).getLastRefreshTime();

		// 마지막 갱신 시간이 현재 시간 기준으로 갱신 시간을 넘겼으면 갱신
		return (Hours.hoursBetween(lastRfTime, DateTime.now()).getHours() >= REFRESH_HOURS);
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
