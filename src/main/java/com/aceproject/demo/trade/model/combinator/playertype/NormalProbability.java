package com.aceproject.demo.trade.model.combinator.playertype;

import java.util.List;

import com.aceproject.demo.common.model.Player;
import com.aceproject.demo.trade.model.PlayerType;
import com.aceproject.demo.trade.model.TradeOption;

public class NormalProbability extends TypeProbability{

	@Override
	public PlayerType getPlayerType(List<Player> players, TradeOption option) {
		return null;
	}

}
