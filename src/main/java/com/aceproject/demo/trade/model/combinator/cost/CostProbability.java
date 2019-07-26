package com.aceproject.demo.trade.model.combinator.cost;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.aceproject.demo.common.model.Player;
import com.aceproject.demo.trade.TradeUtils;
import com.aceproject.demo.trade.model.PlayerType;
import com.aceproject.demo.trade.model.TradeOption;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class CostProbability {
	private static final int DOUBLE_COST_PROBABILITY = 50;
	private static final int TRIPLE_COST_PROBABILITY = 55;
	private static final int QUARTER_COST_PROBABILITY = 60;
	private static final int DEFAULT_COST_PROBABILITY = 5;

	private static final int MAX_COST = 8;
	private static List<CostFilter> costFilters = Arrays.asList(new CostFilter(2, DOUBLE_COST_PROBABILITY),
			new CostFilter(3, TRIPLE_COST_PROBABILITY), new CostFilter(4, QUARTER_COST_PROBABILITY));

	// 코스트에 해당하는 갯수를 세서 성공확률을 반환
	public int getCost(List<Player> players, TradeOption option) {

		// 재료들의 평균 코스트 (소수점 올림 적용)
		int avgCost = (int) Math.ceil(players.stream().mapToInt(Player::getCost).average().getAsDouble());
		Map<Integer, Long> costCntMap = getCountPlayerTypeMap(players);

		// 동일코스트 2명투입시 50%확률로 코스트업 발생 +1명씩할때마다 5%추가 60%확률로 코스트업
		// 2세트를 넣었으면 기회가 2번있음 단, 1번이라도 성공시 종료
		for (Entry<Integer, Long> entry : costCntMap.entrySet()) {
			if (costFilters.stream().anyMatch(c -> c.isCostUp(entry.getValue().intValue())))
				return TradeOption.costUp(avgCost);
		}
		return avgCost;
	}

	// 8 코스트가 두개 이상이 나오면 등급 업을 해줌
	public PlayerType getPlayerType(List<Player> players, PlayerType type) {

		Map<Integer, Long> costCntMap = getCountPlayerTypeMap(players);
		int cnt = costCntMap.getOrDefault(MAX_COST, 0L).intValue();
		if (cnt >= 2 && TradeUtils.isSuccess(cnt * DEFAULT_COST_PROBABILITY))
			return getTypeUp(type);
		return type;
	}

	// 등장가능한 최대등급은 MONSTER. 타입을 업해서 반환
	private PlayerType getTypeUp(PlayerType type) {
		return PlayerType.min(PlayerType.MONSTER, type);
	}

	// 코스트를 세기 위한 자료구조
	Map<Integer, Long> getCountPlayerTypeMap(List<Player> players) {
		return players.stream().collect(Collectors.groupingBy(Player::getCost, Collectors.counting()));

	}

}

@Getter
@AllArgsConstructor
class CostFilter {
	int cnt;
	int percent;

	boolean isCostUp(int cnt) {
		if (this.cnt == cnt && TradeUtils.isSuccess(percent))
			return true;
		return false;
	}

}