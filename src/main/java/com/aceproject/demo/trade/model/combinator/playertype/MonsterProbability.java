package com.aceproject.demo.trade.model.combinator.playertype;

import java.util.List;
import java.util.Map;

import com.aceproject.demo.common.model.Player;
import com.aceproject.demo.trade.TradeUtils;
import com.aceproject.demo.trade.model.PlayerType;
import com.aceproject.demo.trade.model.TradeOption;

public class MonsterProbability extends TypeProbability {

	private static final PlayerType type = PlayerType.MONSTER;

	@Override
	public PlayerType getPlayerType(List<Player> players, TradeOption option) {

		int percent = DEFAULT_PERCENT;
		Map<PlayerType, Long> typeCntMap = getCountPlayerTypeMap(players);
		if(typeCntMap.getOrDefault(type, 0L) == 4)
			percent = 60;
		else if(typeCntMap.getOrDefault(type, 0L) == 3)
			percent = 55;
		else if(typeCntMap.getOrDefault(type, 0L) == 2)
			percent = 50;

		return percent == DEFAULT_PERCENT ? null : TradeUtils.isSuccess(option.getPercent(percent)) ? type : combinateDefaultPlayer();

	}
}
