package com.aceproject.demo.trade.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.aceproject.demo.common.dao.TeamPlayerDao;
import com.aceproject.demo.common.model.TeamPlayer;
import com.aceproject.demo.trade.model.TradeOption;
import com.aceproject.demo.trade.model.TradePlayerView;
import com.aceproject.demo.trade.service.TradeService;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TradeServiceImplTest {

	@Autowired
	private TradeService tradeService;
	
	@Autowired
	private TeamPlayerDao teamPlayerDao;
	
//	@Autowired
//	private PlayerDao playerDao;
	

//	@Test
	public void getTradePlayerTest() {
		
//		System.out.println(PlayerType.NORMAL.getValue());

		List<TradePlayerView> views = tradeService.getTradePlayers(1);
		for (TradePlayerView t : views)
			System.out.println(t.getPlayerId() + " : " + t.getName());
	}

	@Test
	public void tradeTest() {
		
		for (TeamPlayer p : teamPlayerDao.list(1))
			System.out.print(p.getPlayerId() + " ");
		System.out.println();
		
		List<Integer> list = new ArrayList<>();
		list.add(14);
		list.add(16);
		
		TradeOption option = new TradeOption();
		Set<Integer> years = new HashSet<>();
		years.add(2019);
		years.add(2018);
		option.setYears(years);
		option.setPercentUp(false);
			

		tradeService.trade(1, list, option);
		
	}

//	@Test
	public void selected() {
		
		TradeOption option = new TradeOption();
		Set<Integer> years = new HashSet<>();
		years.add(2019);
		years.add(2018);
		option.setYears(years);
		
		List<Integer> list = new ArrayList<>();
		list.add(7);
		list.add(9);
		list.add(10);
		list.add(11);
		
		tradeService.trade(1, list, option);
		

	}

}
