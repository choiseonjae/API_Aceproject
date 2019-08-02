package com.aceproject.demo.trade.model.combinator.playertype;

import java.util.Map;

import com.aceproject.demo.trade.model.PlayerType;
import com.aceproject.demo.trade.model.TradeOption;

public class NormalProbability implements TypeProbability{

	@Override
	public PlayerType getPlayerType(Map<PlayerType, Integer> typeCntMap, TradeOption option) {
		return null;
	}

}
