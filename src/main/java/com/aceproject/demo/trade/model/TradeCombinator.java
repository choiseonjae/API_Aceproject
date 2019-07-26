package com.aceproject.demo.trade.model;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.aceproject.demo.common.model.Player;

public class TradeCombinator {

	private static final int PERCENT = 100;
	private static final int PERCENT_UP = 10;

	private static final int DEFAULT_PLAYER_TYPE = 3;
	private static final int FIFTY_PROBABILITY = 50;
	private static final int FIFTYFIVE_PROBABILITY = 55;
	private static final int SIXTY_PROBABILITY = 60;
	private static final int SIXTYFIVE_PROBABILITY = 65;
	private static final int SEVENTY_PROBABILITY = 70;
	private static final int HUNDRED_PROBABILITY = 100;

	private static final int DOUBLE_COST_PROBABILITY = 50;
	private static final int TRIPLE_COST_PROBABILITY = 55;
	private static final int QUARTER_COST_PROBABILITY = 60;
	private static final int DEFAULT_COST_PROBABILITY = 5;

	private static final int DOUBLE_COST = 2;
	private static final int TRIPLE_COST = 3;
	private static final int QUARTER_COST = 4;
	private static final int MAX_COST = 8;

	private List<Player> players;
	private TradeOption option;

	public TradeCombinator(List<Player> players, TradeOption option) {
		this.players = players;
		this.option = option;
	}

	// Q. 해당 메소드를 static으로 사용했을 경우, typeCntMap가 공유되는 문제가 없는지 궁금합니다.
	public Predicate<Player> tradeFilter() {

		Predicate<Player> filter;

		// 등급을 세기 위한 자료구조 (map)
		Map<PlayerType, Long> typeCntMap = players.stream()
				.collect(Collectors.groupingBy(p -> p.getPlayerType(), Collectors.counting()));

		// 조합법 검사
		BiFunction<PlayerType, Integer, Boolean> cond = (type,
				cnt) -> (typeCntMap.containsKey(type) && Math.toIntExact(typeCntMap.get(type)) == cnt);

		// 초기화
		int percent = 0;
		PlayerType type = null;

		if (cond.apply(PlayerType.ACE, 4)) {
			percent = HUNDRED_PROBABILITY;
			type = PlayerType.ACE;
		} else if (cond.apply(PlayerType.MONSTER, 4)) {
			percent = SIXTY_PROBABILITY;
			type = PlayerType.MONSTER;
		} else if (cond.apply(PlayerType.MONSTER, 3)) {
			percent = FIFTYFIVE_PROBABILITY;
			type = PlayerType.MONSTER;
		} else if (cond.apply(PlayerType.MONSTER, 2)) {
			percent = FIFTY_PROBABILITY;
			type = PlayerType.MONSTER;
		} else if (cond.apply(PlayerType.SPECIAL, 4)) {
			percent = SEVENTY_PROBABILITY;
			type = PlayerType.SPECIAL;
		} else if (cond.apply(PlayerType.SPECIAL, 3)) {
			percent = SIXTYFIVE_PROBABILITY;
			type = PlayerType.SPECIAL;
		} else if (cond.apply(PlayerType.SPECIAL, 2)) {
			percent = SIXTY_PROBABILITY;
			type = PlayerType.SPECIAL;
		}

		if (option.isPercentUp())
			percent += PERCENT_UP;

		// 투입된 playerType에 의한 선수등급 선정
		type = isSuccess(percent) ? type : combinateDefaultPlayer();

		// 코스트 관련 조합법 계산
		double sum = players.stream().map(p -> p.getCost()).mapToDouble(Integer::intValue).sum();
		int tmpCost = (int) Math.ceil(sum / players.size());

		// 만약 코스트 업을 신청 했을 경우, 원래 평균 값에서 + 1을 한다.
		int cost = option.isCostUp() ? TradeOption.costUp(tmpCost) : tmpCost;

		// 동일코스트 2명투입시 50%확률로 코스트업 발생 +1명씩할때마다 5%추가 60%확률로 코스트업
		// 2세트를 넣었으면 기회가 2번있음 단, 1번이라도 성공시 종료
		Map<Integer, Long> cntCostMap = players.stream()
				.collect(Collectors.groupingBy(p -> p.getCost(), Collectors.counting()));

		for (Entry<Integer, Long> entry : cntCostMap.entrySet()) {
			if (entry.getValue() == DOUBLE_COST && isSuccess(DOUBLE_COST_PROBABILITY)) {
				cost = TradeOption.costUp(cost);
				break;
			} else if (entry.getValue() == TRIPLE_COST && isSuccess(TRIPLE_COST_PROBABILITY)) {
				cost = TradeOption.costUp(cost);
				break;
			} else if (entry.getValue() == QUARTER_COST && isSuccess(QUARTER_COST_PROBABILITY)) {
				cost = TradeOption.costUp(cost);
				break;
			}
		}

		// TODO 랜덤으로 출력한 등급의 상위등급 or 입력받은 선수들의 상위등급?
		// 8코스트 2명투입시 10%확률로 상위등급 등장 +1명씩 할때마다 5%확률 추가 최대 20%확률 (에이스 제외)
		// 등장할 상위등급의 코스트는 평균치를 따름
		// 캐시 옵션 적용되어야 함
		if (cntCostMap.containsKey(MAX_COST) && cntCostMap.get(MAX_COST) >= DOUBLE_COST)
			if (isSuccess(Math.toIntExact(cntCostMap.get(MAX_COST)) * DEFAULT_COST_PROBABILITY))
				type = upType(type);

		// 성공 혹은 실패 시 결정된 등급 반환
		filter = equalFilter(type);
//		filter = isSuccess(percent) ? equalFilter(type) : equalFilter(downType(type));

		// TODO 삭제
		System.out.println(type + " : " + cost);

		final int resultCost = cost;
		// 코스트 필터 반환
		return filter.and(p -> p.getCost() == resultCost);

	}

	private PlayerType upType(PlayerType result) {
		// 등장가능한 최대등급은 MONSTER. ACE는 특별조합으로만 등장함
		return PlayerType.valueOf(Math.min(PlayerType.MONSTER.intValue(), result.intValue() + 1));
	}

	private PlayerType combinateDefaultPlayer() {
		return PlayerType.valueOf((int) (Math.random() * DEFAULT_PLAYER_TYPE) + 1);
	}

	private Predicate<Player> equalFilter(PlayerType type) {
		return p -> p.getPlayerType() == type;
	}

	private boolean isSuccess(int percent) {
		return (int) (Math.random() * PERCENT) < percent;
	}

}
