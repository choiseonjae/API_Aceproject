package com.aceproject.demo.trade.model.combinator;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import com.aceproject.demo.common.model.Player;
import com.aceproject.demo.trade.model.PlayerType;
import com.aceproject.demo.trade.model.TradeOption;
import com.aceproject.demo.trade.model.combinator.cost.CostProbability;
import com.aceproject.demo.trade.model.combinator.playertype.AceProbability;
import com.aceproject.demo.trade.model.combinator.playertype.MonsterProbability;
import com.aceproject.demo.trade.model.combinator.playertype.NormalProbability;
import com.aceproject.demo.trade.model.combinator.playertype.SpecialProbability;
import com.aceproject.demo.trade.model.combinator.playertype.TypeProbability;

public class TradeCombinator {

	private static final List<TypeProbability> typeProbs = Arrays.asList(
			new AceProbability()
			, new MonsterProbability()
			, new SpecialProbability()
			, new NormalProbability());

	private static final CostProbability costProb = new CostProbability();
	
	public Predicate<Player> tradeFilter(List<Player> players, TradeOption option) {

		// player 등급 확률 적용 후 반환
		PlayerType type = null;
		for(TypeProbability tp : typeProbs) {
			type = tp.getPlayerType(players, option);
			if(type != null) break;
		}
		
		// 조합이 존재 하지 않을 경우
		if (type == null)
			type = TypeProbability.combinateDefaultPlayer();
					
		// 코스트 옵션 적용
		int cost = costProb.getCost(players, option);
		
		// 8코스트 2개 이상시 등급 업 확률 적용
		type = costProb.getPlayerType(players, type);
		
		// 최종 등급과 코스트 필터 반환
		final PlayerType resultType = type; 
		Predicate<Player> filter = p -> p.getPlayerType() == resultType;
		return filter.and(p -> p.getCost() == cost);

	}

}
