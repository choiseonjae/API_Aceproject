package com.aceproject.demo.trade.model.combinator.cost;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import com.aceproject.demo.common.model.Player;
import com.aceproject.demo.trade.TradeUtils;
import com.aceproject.demo.trade.model.TradeOption;

public class CostProbability {
	private static final int DOUBLE_COST_PROBABILITY = 50;
	private static final int TRIPLE_COST_PROBABILITY = 55;
	private static final int QUARTER_COST_PROBABILITY = 60;
	private static List<CostFilter> COST_FILTERS = Arrays.asList(
			
			new CostFilter(2, DOUBLE_COST_PROBABILITY),
			
			new CostFilter(3, TRIPLE_COST_PROBABILITY), 
			
			new CostFilter(4, QUARTER_COST_PROBABILITY)
	);

	// 코스트에 해당하는 갯수를 세서 성공확률을 반환
	public int getCost(List<Player> players, TradeOption option) {
		Function<Player, Integer> costConverter = p -> p.getCost();

		// 재료들의 평균 코스트 (소수점 올림 적용)
		int avgCost = (int) Math.ceil(players.stream().mapToInt(Player::getCost).average().getAsDouble());
		// TODO CostCountMap 을 사용해야 하는데 type을 셈.
		Map<Integer, Integer> costCntMap = TradeUtils.getCountMap(players, costConverter);

		// 동일코스트 2명투입시 50%확률로 코스트업 발생 +1명씩할때마다 5%추가 60%확률로 코스트업
		// 2세트를 넣었으면 기회가 2번있음 단, 1번이라도 성공시 종료
		for (Entry<Integer, Integer> entry : costCntMap.entrySet()) {
			if (COST_FILTERS.stream().anyMatch(c -> c.isCostUp(entry.getValue().intValue())))
				return TradeOption.costUp(avgCost);
		}
		return avgCost;
	}

}
