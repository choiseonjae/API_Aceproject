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
	 * team이 소유한 player들을 재료로 사용하여, 새로운 선수를 영입한다.
	 * player는 2 ~ 4까지 재료로 사용가능하며, 새로운 선수는 재료 player의 등급들의 조합법에 따른 
	 * 확률을 적용하여 새로운 선수를 영입한다.
	 * 
	 * @param teamId
	 * @param playerIds
	 * @param tradeOption
	 * @return
	 */
	TradePlayerView trade(int teamId, List<Integer> playerIds, TradeOption tradeOption);

}
