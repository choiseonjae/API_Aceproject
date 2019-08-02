package com.aceproject.demo.trade.model.combinator.playertype;

import java.util.Map;

import com.aceproject.demo.trade.TradeUtils;
import com.aceproject.demo.trade.model.PlayerType;
import com.aceproject.demo.trade.model.TradeOption;

public class MonsterProbability implements TypeProbability {

	private static final PlayerType type = PlayerType.MONSTER;

	@Override
	public PlayerType getPlayerType(Map<PlayerType, Integer> typeCntMap, TradeOption option) {

		int percent = DEFAULT_PERCENT;
		if (typeCntMap.getOrDefault(type, 0) == 4)
			percent = 60;
		else if (typeCntMap.getOrDefault(type, 0) == 3)
			percent = 55;
		else if (typeCntMap.getOrDefault(type, 0) == 2)
			percent = 50;

		return percent == DEFAULT_PERCENT ? null
				: TradeUtils.isSuccess(option.getPercent(percent)) ? type : TradeUtils.combinateDefaultPlayer();

	}

}
