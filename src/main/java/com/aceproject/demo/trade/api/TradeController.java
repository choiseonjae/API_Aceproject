package com.aceproject.demo.trade.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aceproject.demo.trade.model.TradeOption;
import com.aceproject.demo.trade.model.TradePlayerView;
import com.aceproject.demo.trade.service.TradeService;

@RestController
@RequestMapping("/api/trade")
public class TradeController {

	@Autowired
	private TradeService tradeService;

	@GetMapping("/list")
	public List<TradePlayerView> getTradePlayers(@RequestParam(value = "teamId") int teamId) {
		System.out.println(teamId);
		return tradeService.getTradePlayers(teamId);
	}
	
	@PostMapping("")
	public TradePlayerView trade(@RequestParam int teamId, @RequestParam List<Integer> playerIds, @RequestParam(required = false)TradeOption tradeOption ){
		if(tradeOption == null)
			tradeOption = new TradeOption();
		return tradeService.trade(teamId, playerIds, tradeOption);
	}
	
	
}
