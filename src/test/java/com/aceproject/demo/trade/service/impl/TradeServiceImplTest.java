package com.aceproject.demo.trade.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.aceproject.demo.common.exception.NotEnoughPlayerException;
import com.aceproject.demo.trade.exception.NotEnoughTradeYearException;
import com.aceproject.demo.trade.model.TradeOption;
import com.aceproject.demo.trade.model.TradePlayerView;
import com.aceproject.demo.trade.service.TradeService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TradeServiceImplTest {

	@Autowired
	private TradeService tradeService;
	
	//tradePlayerView를 잘 갖고 오는지에 대한 테스트
	@Test
	public void getTradePlayerTest() {
		List<TradePlayerView> views = tradeService.getTradePlayers(1);
		for (TradePlayerView t : views)
			System.out.println(t.getPlayerId() + " : " + t.getName());
	}

	
	//player 수를 1명 이하로 설정했을 때 충분하지 않은 선수 예외처리 테스트
	@Test(expected = NotEnoughPlayerException.class)
	public void notEnoughPlayerTest() {
		List<Integer> playerIds = new ArrayList<>();
		playerIds.add(14);
		
		Set<Integer> years = new HashSet<>();
		TradeOption option = new TradeOption(years, false, false);
		
		tradeService.trade(1, playerIds, option);
	}
	
	//년도를 1게 이하로 설정했을 때 충분하지 않은 년도옵션 예외처리 테스트
	@Test(expected = NotEnoughTradeYearException.class)
	public void notEnoughTradeYearTest() {
		List<Integer> playerIds = new ArrayList<>();
		playerIds.add(14);
		playerIds.add(16);
		
		Set<Integer> years = new HashSet<>();
		years.add(2015);
		TradeOption option = new TradeOption(years, false, false);
		
		tradeService.trade(1, playerIds, option);
	}
	
	//재료로 선택한 player를 teamplayer에서 삭제하고 뽑힌 선수를 반영하는지에 대한 테스트
	@Test
	public void tradeTest() {
		List<TradePlayerView> views = tradeService.getTradePlayers(1);
		
		System.out.println("-------------before Trade-----------------------------------");
		for (TradePlayerView t : views)
			System.out.println(t.getPlayerId() + " : " + t.getName());
		
		List<Integer> teamPlayerIds = views.stream().map(v -> v.getPlayerId()).collect(Collectors.toList());
		
		//teamplayer에 트레이드 할 선수가 충분하지 않은 경우
		if(teamPlayerIds.size() <= 1) {
			//teamplayer에 선수 add
		} 
		
		//갖고 있는 선수 중 가장 앞에 있는 두 선수를 골라 트레이드를 한다.
		List<Integer> playerIds = new ArrayList<>();
		playerIds.add(teamPlayerIds.get(0));
		playerIds.add(teamPlayerIds.get(1));
		
		Set<Integer> years = null;
		TradeOption option = new TradeOption(years, false, false);

		TradePlayerView selectedPlayer = tradeService.trade(1, playerIds, option);
		
		//삭제가 되었는지 확인
		views = tradeService.getTradePlayers(1);
		
		System.out.println("-------------after Trade-----------------------------------");
		for (TradePlayerView t : views)
			System.out.println(t.getPlayerId() + " : " + t.getName());
		
		System.out.println("-------------selected Player-----------------------------------");
		System.out.println(selectedPlayer.getPlayerId() + " : " + selectedPlayer.getName());
	}

	//해당하는 연도와 조건으로 선수를 뽑는지에 대한 테스트
	@Test
	public void selectePlayerTest() {
		
	}

}
