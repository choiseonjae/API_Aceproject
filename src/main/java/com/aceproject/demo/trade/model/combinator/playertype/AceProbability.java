package com.aceproject.demo.trade.model.combinator.playertype;

import java.util.Map;

import com.aceproject.demo.trade.model.PlayerType;
import com.aceproject.demo.trade.model.TradeOption;

public class AceProbability implements TypeProbability{
	
	private static final PlayerType type = PlayerType.ACE;
	
	@Override
	public PlayerType getPlayerType(Map<PlayerType, Integer> typeCntMap, TradeOption option) {
		
		if(typeCntMap.getOrDefault(type, 0) == 4)
			return type;
		
		return null;
	}

}
