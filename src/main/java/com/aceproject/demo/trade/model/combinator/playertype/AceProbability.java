package com.aceproject.demo.trade.model.combinator.playertype;

import java.util.List;
import java.util.Map;

import com.aceproject.demo.common.model.Player;
import com.aceproject.demo.trade.model.PlayerType;
import com.aceproject.demo.trade.model.TradeOption;

public class AceProbability extends TypeProbability{
	
	private static final PlayerType type = PlayerType.ACE;
	
	@Override
	public PlayerType getPlayerType(List<Player> players, TradeOption option) {
		
		Map<PlayerType, Long> typeCntMap = getCountPlayerTypeMap(players);
		if(typeCntMap.getOrDefault(type, 0L) == 4)
			return type;
		
		return null;
	}

}
