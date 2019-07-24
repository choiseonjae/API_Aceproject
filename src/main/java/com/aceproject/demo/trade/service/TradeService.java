package com.aceproject.demo.trade.service;

import java.util.List;

import com.aceproject.demo.trade.model.TradeOption;
import com.aceproject.demo.trade.model.TradePlayerView;

public interface TradeService {

	/**
	 * team이 보유한 trade 가능한 선수 목록을 조회
	 * 
	 * @param teamId
	 * @return
	 */
	List<TradePlayerView> getTradePlayers(int teamId);

	/**
	 * team이 소유한 선수들을 소모하여, 새로운 선수를 생성한다.
	 * 
	 * @param teamId
	 * @param playerIds
	 * @param tradeOption
	 * @return
	 */
	TradePlayerView trade(int teamId, List<Integer> playerIds, TradeOption tradeOption);

}
