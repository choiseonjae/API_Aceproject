package com.aceproject.demo.trade.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aceproject.demo.common.dao.PersonDao;
import com.aceproject.demo.common.dao.PlayerDao;
import com.aceproject.demo.common.dao.TeamPlayerDao;
import com.aceproject.demo.common.model.Person;
import com.aceproject.demo.common.model.Player;
import com.aceproject.demo.common.model.TeamPlayer;
import com.aceproject.demo.trade.exception.AlreadyTradedPlayerException;
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

	private static final int MIN_TRADE_PLAYER_COUNT = 2;

	@Override
	public List<TradePlayerView> getTradePlayers(int teamId) {

		// 팀 아이디로 현재 teamplayer DB 반환
		List<TeamPlayer> teamPlayers = teamPlayerDao.list(teamId);

		// 플레이어가 트레이드에 필요한 선수만큼 소유하고 있지 않은 경우 예외 발생
		if (teamPlayers.size() < MIN_TRADE_PLAYER_COUNT) {
			throw new AlreadyTradedPlayerException();
		}

		// team이 가지고 있는 palyerId 만을 추출하여, DB에 쿼리를 날려 가져옴
		// 전체를 가져오지 않고, 필요한 정보만을 쿼리를 통해 가져옴.
		List<Integer> playerIds = teamPlayers.stream().map(s -> s.getPlayerId()).collect(Collectors.toList());
		List<Player> players = playerDao.list(playerIds);
	
		// 모든 사람 정보를 가져와서 teamPlayer의 사람 정보만을 추출
		Map<Integer, Person> selectedPersonsMap = personDao.getAll().stream().collect(Collectors.toMap(p -> p.getPersonId(), p -> p));

		// team이 소유하고 있는 선수의 Person 객체와 Player 객체를 View 형태로 반환
		return players.stream().map(p -> {
			TradePlayerView tradePlayerView = new TradePlayerView();
			tradePlayerView.setPerson(selectedPersonsMap.get(p.getPersonId()));
			tradePlayerView.setPlayer(p);
			return tradePlayerView;
		}).collect(Collectors.toList());
		
	}

	@Override
	@Transactional
	public TradePlayerView trade(int teamId, List<Integer> playerIds, TradeOption tradeOption) {
		
		tradeOption.checkException();
		// 랜덤선수로 뽑고 팀플레이어에 추가
		Player selectedPlayer = selectCombinedPlayer(playerIds, tradeOption);

		// 선택한 선수 DB에 반영
		TeamPlayer teamPlayer = new TeamPlayer(teamId, selectedPlayer.getPlayerId());
		addTeamPlayer(teamPlayer);

		//이미 트레이드한 선수에 대한 예외 처리
		Set<Integer> playerIdSet = teamPlayerDao.list(teamId).stream().map( p -> p.getPlayerId() ).collect(Collectors.toSet());
		
		playerIds.stream().forEach( p -> {
			if(!playerIdSet.contains(p))
				throw new AlreadyTradedPlayerException();
		});

		// 선수들에 대한 아이디를 받아 팀플레이어에서 삭제
		teamPlayerDao.delete(teamId, playerIds);

		TradePlayerView tradePlayerView = new TradePlayerView();
		tradePlayerView.setPerson(personDao.get(selectedPlayer.getPersonId()));
		tradePlayerView.setPlayer(selectedPlayer);
		return tradePlayerView;

	}

	public Player selectCombinedPlayer(List<Integer> playerIds, TradeOption option) {
		option.checkException();
		List<Player> players = playerDao.yearList(option.getYears());
		
		int rand = -1;
		while (true) {
			rand = (int) (Math.random() * players.size());

			if (playerIds.contains(players.get(rand).getPlayerId()))
				continue;
			break;
		}
		return players.get(rand);

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
