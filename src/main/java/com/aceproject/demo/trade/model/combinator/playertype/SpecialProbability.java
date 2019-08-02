package com.aceproject.demo.trade.model.combinator.playertype;

import java.util.List;
import java.util.Map;

import com.aceproject.demo.common.model.Player;
import com.aceproject.demo.trade.TradeUtils;
import com.aceproject.demo.trade.model.PlayerType;
import com.aceproject.demo.trade.model.TradeOption;

public class SpecialProbability implements TypeProbability {

	private static final PlayerType type = PlayerType.SPECIAL;
	
	@Override
	public PlayerType getPlayerType(Map<PlayerType, Integer> typeCntMap, TradeOption option) {
		
		int percent = DEFAULT_PERCENT;
		if(typeCntMap.getOrDefault(type, 0) == 4)
			percent = 70;
		else if(typeCntMap.getOrDefault(type, 0) == 3)
			percent = 65;
		else if(typeCntMap.getOrDefault(type, 0) == 2)
			percent = 60;

		return percent == DEFAULT_PERCENT ? null : TradeUtils.isSuccess(option.getPercent(percent)) ? type : TradeUtils.combinateDefaultPlayer();

	}

}
