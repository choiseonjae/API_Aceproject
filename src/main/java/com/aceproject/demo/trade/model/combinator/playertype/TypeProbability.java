package com.aceproject.demo.trade.model.combinator.playertype;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.aceproject.demo.common.model.Player;
import com.aceproject.demo.trade.model.PlayerType;
import com.aceproject.demo.trade.model.TradeOption;

public abstract class TypeProbability {

	static final int PERCENT = 100;
	static final int DEFAULT_PLAYER_TYPE = 3;
	static final int MAX_COST = 8;
	static final int PERCENT_UP = 10;
	static final int DEFAULT_PERCENT = 0;

	public abstract PlayerType getPlayerType(List<Player> players, TradeOption option);

	public static PlayerType combinateDefaultPlayer() {
		return PlayerType.valueOf((int) (Math.random() * DEFAULT_PLAYER_TYPE) + 1);
	}

	// 등급을 세기 위한 자료구조 (map)
	Map<PlayerType, Long> getCountPlayerTypeMap(List<Player> players){
		return players.stream().collect(Collectors.groupingBy(Player::getPlayerType, Collectors.counting()));

	}
	
}