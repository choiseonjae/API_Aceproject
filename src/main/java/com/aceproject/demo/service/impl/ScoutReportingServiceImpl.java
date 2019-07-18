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

import com.aceproject.demo.dao.PersonDao;
import com.aceproject.demo.dao.PlayerDao;
import com.aceproject.demo.dao.TeamDao;
import com.aceproject.demo.dao.TeamPlayerDao;
import com.aceproject.demo.dao.TeamSrSlotDao;
import com.aceproject.demo.exception.NotEnoughPlayerException;
import com.aceproject.demo.model.Person;
import com.aceproject.demo.model.Player;
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

	@Override
	@Transactional
	public void contract(int teamId, int slotNo) {

		// slots 조회
		List<TeamSrSlot> srSlots = teamSrSlotDao.list(teamId);

		// 내가 보유하고 있을 시 해당 선수의 level + 1

		// 내가 사려고 하는 slot과 동일하면 영입 후 종료
		for (TeamSrSlot srSlot : srSlots) {
			if (srSlot.getSlotNo() == slotNo) {
				srSlot.setContractYN(YN.Y);
				teamSrSlotDao.update(srSlot);
				teamPlayerDao.list(teamId).stream().forEach(tp -> {
					if (tp.getPlayerId() == srSlot.getPlayerId()) {
						tp.setLevel(tp.getLevel() + 1);
						teamPlayerDao.update(tp);
						return;
					}
				});
				// 이거 두번 먹힐 거 같은데 어떻게 하지 존재하면 할려면 다시 생각해 보
				teamPlayerDao.insert(new TeamPlayer(srSlot.getTeamId(), srSlot.getPlayerId(), INIT_LEVEL, INIT_EXP));
				return;
			}
		}

	}

	@Override
	public List<TeamSrSlotView> getSrSlots(int teamId) {

		// 마지막 refresh 시간 조회
		DateTime lastRfTime = teamDao.get(teamId).getLastRefreshTime();

		// slots 조회
		List<TeamSrSlot> srSlots = teamSrSlotDao.list(teamId);

		// 갱신이 필요하거나 slots이 비어있을 경우 refresh
		if (isRefresh(lastRfTime) || srSlots.isEmpty())
			return refresh(teamId);

		// 현재 상태 반환
		return getTeamSrSlotViews(srSlots);
	}

	private static final int MAX_SLOT_NO = 4;

	@Override
	@Transactional
	public List<TeamSrSlotView> refresh(int teamId) {
		// 전체 person,player 정보 조회
		List<Player> players = playerDao.getAll();
		List<Person> persons = personDao.getAll();

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

		// 기존 slots 존재 여부 조회후 DB 반영
		boolean isEmptySlot = teamSrSlotDao.list(teamId).isEmpty();
		if (isEmptySlot)
			teamSrSlotDao.insert(refreshSlots);
		else
			teamSrSlotDao.update(refreshSlots);

		// 마지막 갱신 시간 갱신
		Team team = teamDao.get(teamId);
		team.setLastRefreshTime(DateTime.now());
		teamDao.update(team);

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

}
