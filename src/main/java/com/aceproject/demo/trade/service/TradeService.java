package com.aceproject.demo.trade.service;

import java.util.List;

import com.aceproject.demo.trade.model.TradeOption;
import com.aceproject.demo.trade.model.TradePlayerView;

public interface TradeService {
	
	List<TradePlayerView> getTradePlayers(int teamId);
	
	TradePlayerView trade(int teamId, List<Integer> playerIds, TradeOption tradeOption);

}
