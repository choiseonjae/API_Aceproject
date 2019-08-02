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
import com.aceproject.demo.trade.model.TradeOption;
import com.aceproject.demo.trade.model.TradePlayerView;
import com.aceproject.demo.trade.model.combinator.TradeCombinator;
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

		// 팀이 현재 보유하고 있는 player를 가져와서 client view 형태로 반환
		List<TeamPlayer> teamPlayers = teamPlayerDao.list(teamId);

		// 해당 team이 소유한 player 만 조회
		List<Integer> teamPlayerIds = teamPlayers.stream().map(s -> s.getPlayerId()).collect(Collectors.toList());
		List<Player> players = playerDao.list(teamPlayerIds);

		// 필요한 perosn 정보만 조회
		List<Integer> personIds = players.stream().map(Player::getPersonId).distinct().collect(Collectors.toList());
		List<Person> people = personDao.list(personIds);
	
		// persondId -> person 매핑
		Map<Integer, Person> personMap = people.stream().collect(Collectors.toMap(Person::getPersonId, p -> p));

		return players.stream().map(p -> new TradePlayerView(personMap.get(p.getPersonId()), p))
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

		// cash, ap 차감 시 cash 및 ap 보유 여부 확인 후 차감
		if (option.getPercentCash() != 0)
			accountService.deductCash(teamId, option.getPercentCash());
		if (option.getCostUpAP() != 0)
			accountService.deductAp(teamId, option.getCostUpAP());

		// 예외 : 트레이드 할 선수 목록을 현재 플레이어가 소유 여부 확인
		Set<Integer> playerIdSet = teamPlayerDao.list(teamId).stream().map(TeamPlayer::getPlayerId).collect(Collectors.toSet());
		boolean notExist = playerIds.stream().anyMatch(pId -> !playerIdSet.contains(pId));
		if (notExist)
			throw new AlreadyTradedPlayerException();

		// 선수들에 대한 아이디를 받아 팀플레이어에서 삭제
		teamPlayerDao.delete(teamId, playerIds);

		// 조합법에 따라 선수를 뽑음
		Player selectedPlayer = selectPlayer(playerIds, option);

		// 선택한 선수 DB에 반영
		TeamPlayer teamPlayer = new TeamPlayer(teamId, selectedPlayer.getPlayerId());
		addTeamPlayer(teamPlayer);

		// client view 형태로 반환
		Person person = personDao.get(selectedPlayer.getPersonId());
		return new TradePlayerView(person, selectedPlayer);

	}

	private TradeCombinator combinator = new TradeCombinator();

	private Player selectPlayer(List<Integer> playerIds, TradeOption option) {

		// 해당 하는 연도의 선수만 조회
		List<Player> yearPlayers = playerDao.yearList(option.getYears());
		
		// 트레이드 재료인 선수들을 조합하여 하나의 등급을 선택
		List<Player> players = playerDao.list(playerIds);

		Predicate<Player> tradeFilter = combinator.tradeFilter(players, option);
		Predicate<Player> playerFilter = p -> !playerIds.contains(p.getPlayerId());
		yearPlayers = yearPlayers.stream().filter(playerFilter.and(tradeFilter)).collect(Collectors.toList());

		for(Player p : yearPlayers) {
			System.out.println(p.getPlayerId() + " : ");
		}
		
		// 예외 : 필터 후 영입 가능한 선수가 존재 하지 않는 경우
		if (yearPlayers.isEmpty())
			throw new NotEnoughConditionPlayerException();

		int selectedIdx = (int) (Math.random() * yearPlayers.size());
		return yearPlayers.get(selectedIdx);

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
