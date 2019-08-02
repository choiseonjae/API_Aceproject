package com.aceproject.demo.trade.model.combinator.playertype;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.aceproject.demo.common.model.Player;
import com.aceproject.demo.trade.TradeUtils;
import com.aceproject.demo.trade.model.PlayerType;
import com.aceproject.demo.trade.model.TradeOption;

public interface TypeProbability {

	static final int PERCENT = 100;
	static final int DEFAULT_PLAYER_TYPE = 3;
	static final int MAX_COST = 8;
	static final int PERCENT_UP = 10;
	static final int DEFAULT_PERCENT = 0;
	static final int DEFAULT_COST_PROBABILITY = 5;

	public PlayerType getPlayerType(Map<PlayerType, Integer> typeCntMap, TradeOption option);

	static PlayerType getPlayerTypeUpByCost(List<Player> players, PlayerType type, TradeOption option) {
		// cost 를 count
		Map<Integer, Integer> costCntMap = TradeUtils.getCountMap(players, TradeUtils.costConverter);
		// 8 코스트 개수 확인 후 등급 up 조건 실행
		int cnt = costCntMap.getOrDefault(MAX_COST, 0).intValue();
		int percent = option.getPercent(cnt * DEFAULT_COST_PROBABILITY);
		return (cnt >= 2 && TradeUtils.isSuccess(percent)) ? getTypeUp(type) : type;
	}

	// 등장가능한 최대등급은 MONSTER. 타입을 업해서 반환
	static PlayerType getTypeUp(PlayerType type) {
		return PlayerType.valueOf(Math.min(PlayerType.MONSTER.intValue(), type.intValue()));
	}
	
	

}