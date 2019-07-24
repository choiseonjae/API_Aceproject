package com.aceproject.demo.trade.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aceproject.demo.common.dao.PersonDao;
import com.aceproject.demo.common.dao.PlayerDao;
import com.aceproject.demo.common.dao.TeamPlayerDao;
import com.aceproject.demo.common.exception.NotEnoughPlayerException;
import com.aceproject.demo.common.model.Person;
import com.aceproject.demo.common.model.Player;
import com.aceproject.demo.common.model.TeamPlayer;
import com.aceproject.demo.common.service.AccountService;
import com.aceproject.demo.trade.exception.AlreadyTradedPlayerException;
import com.aceproject.demo.trade.exception.NotEnoughConditionPlayerException;
import com.aceproject.demo.trade.model.PlayerType;
import com.aceproject.demo.trade.model.PlayerTypeCombinator;
import com.aceproject.demo.trade.model.TradeOption;
import com.aceproject.demo.trade.model.TradePlayerView;
import com.aceproject.demo.trade.service.TradeService;

@Service
public class TradeServiceImpl implements TradeService {

	@Autowired
	private PlayerDao playerDao;

	@Autowired
	private TeamPlayerDao teamPlayerDao;

	@Autowired
	private PersonDao personDao;
	
	@Autowired
	private AccountService accountService;

	private static final int MIN_TRADE_PLAYER_COUNT = 2;

	@Override
	public List<TradePlayerView> getTradePlayers(int teamId) {

		// 모든 사람 정보를 가져와서 teamPlayer의 사람 정보만을 추출
		List<Person> persons = personDao.getAll();
		Map<Integer, Person> selectedPersonsMap = persons.stream()
				.collect(Collectors.toMap(p -> p.getPersonId(), p -> p));

		// 팀이 현재 보유하고 있는 player를 가져와서 client view 형태로 반환
		List<TeamPlayer> teamPlayers = teamPlayerDao.list(teamId);
		List<Integer> teamPlayerIds = teamPlayers.stream().map(s -> s.getPlayerId()).collect(Collectors.toList());
		List<Player> players = playerDao.list(teamPlayerIds);

		return players.stream().map(p -> new TradePlayerView(selectedPersonsMap.get(p.getPersonId()), p))
				.collect(Collectors.toList());

	}

	@Override
	@Transactional
	public TradePlayerView trade(int teamId, List<Integer> playerIds, TradeOption option) {

		// 예외 : 플레이어가 트레이드에 필요한 선수 소유 여부 확인
		if (playerIds.size() < MIN_TRADE_PLAYER_COUNT)
			throw new NotEnoughPlayerException();

		// 예외 : 필요한 년도 설정 수 확인
		option.checkYearException();

		// 예외 : 트레이드 할 선수 목록을 현재 플레이어가 소유 여부 확인
		Set<Integer> playerIdSet = teamPlayerDao.list(teamId).stream().map(p -> p.getPlayerId()).collect(Collectors.toSet());
		playerIds.forEach(p -> {
			if (!playerIdSet.contains(p))
				throw new AlreadyTradedPlayerException();
		});

		// 조합법에 따라 선수를 뽑음
		Player selectedPlayer = selectPlayer(playerIds, option);

		// 선택한 선수 DB에 반영
		TeamPlayer teamPlayer = new TeamPlayer(teamId, selectedPlayer.getPlayerId());
		addTeamPlayer(teamPlayer);
		
		accountService.deductCash(teamId, option.getDeductCash());

		// 선수들에 대한 아이디를 받아 팀플레이어에서 삭제
		teamPlayerDao.delete(teamId, playerIds);

		// client view 형태로 반환
		return new TradePlayerView(personDao.get(selectedPlayer.getPersonId()), selectedPlayer);

	}

	public Player selectPlayer(List<Integer> playerIds, TradeOption option) {

		// 해당 하는 연도의 선수만 조회
		List<Player> players = playerDao.yearList(option.getYears());
		
		// 트레이드 재료인 선수들을 조합하여 하나의 등급을 선택
		List<PlayerType> playerTypes = playerDao.list(playerIds).stream().map(p -> p.getPlayerType()).collect(Collectors.toList());
		PlayerTypeCombinator ptc = new PlayerTypeCombinator(); 
		PlayerType selectedPlayerType = ptc.combinate(playerTypes, option.isPercentUp());

		// 선택된 등급 && 재료로 사용된 플레이어 필터
		Predicate<Player> playerTypeFilter = p -> p.getPlayerType() == selectedPlayerType;
		Predicate<Player> playerFilter = p -> playerIds.contains(p.getPlayerId());
		players = players.stream().filter(playerFilter.and(playerTypeFilter)).collect(Collectors.toList());

		// 예외 : 필터 후 선택할 선수가 존재 하지 않는 경우
		if (players.isEmpty())
			throw new NotEnoughConditionPlayerException();

		int selectedIdx = (int) (Math.random() * players.size());
		return players.get(selectedIdx);

	}

	private void addTeamPlayer(TeamPlayer teamPlayer) {
		TeamPlayer check = teamPlayerDao.get(teamPlayer.getTeamId(), teamPlayer.getPlayerId());
		if (check == null)
			teamPlayerDao.insert(teamPlayer);
		else {
			teamPlayer.levelUp();
			teamPlayerDao.update(teamPlayer);
		}
	}

}
