package com.aceproject.demo.trade.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class PlayerTypeCombinator {

	private static final int FIFTY_PROBABILITY = 50;
	private static final int FIFTYFIVE_PROBABILITY = 55;
	private static final int SIXTY_PROBABILITY = 60;
	private static final int SIXTYFIVE_PROBABILITY = 65;
	private static final int SEVENTY_PROBABILITY = 70;
	private static final int HUNDRED_PROBABILITY = 100;
	private static final int PERCENT = 100;
	private static final int INIT = 0;
	private static final int PERCENT_UP = 10;
	private static final int DEFAULT_PLAYER_TYPE_COUNT = 3;
	
	// Q. 해당 메소드를 static으로 사용했을 경우, typeCntMap가 공유되는 문제가 없는지 궁금합니다.
	public PlayerType combinate(List<PlayerType> playerTypes, boolean isPercentUp) {

		// 등급을 세기 위한 자료구조 (map)
		final Map<PlayerType, Integer> typeCntMap = new HashMap<>();
		initMap(typeCntMap);
		
		// 조합법 검사
		BiFunction<PlayerType, Integer, Boolean> cond = (type, cnt) ->  typeCntMap.get(type) == cnt;

		// 각 등급 개수 확인
		playerTypes.forEach(p -> typeCntMap.put(p, typeCntMap.get(p) + 1));
		
		// 초기화
		int tmpPercent = 0;
		PlayerType tmpType;

		if (cond.apply(PlayerType.ACE, 4)) {
			tmpPercent = HUNDRED_PROBABILITY;
			tmpType = PlayerType.ACE;
		} else if (cond.apply(PlayerType.MONSTER, 4)) {
			tmpPercent = SIXTY_PROBABILITY;
			tmpType = PlayerType.MONSTER;
		} else if (cond.apply(PlayerType.MONSTER, 3)) {
			tmpPercent = FIFTYFIVE_PROBABILITY;
			tmpType = PlayerType.MONSTER;
		} else if (cond.apply(PlayerType.MONSTER, 2)) {
			tmpPercent = FIFTY_PROBABILITY;
			tmpType = PlayerType.MONSTER;
		} else if (cond.apply(PlayerType.SPECIAL, 4)) {
			tmpPercent = SEVENTY_PROBABILITY;
			tmpType = PlayerType.SPECIAL;
		} else if (cond.apply(PlayerType.SPECIAL, 3)) {
			tmpPercent = SIXTYFIVE_PROBABILITY;
			tmpType = PlayerType.SPECIAL;
		} else if (cond.apply(PlayerType.SPECIAL, 2)) {
			tmpPercent = SIXTY_PROBABILITY;
			tmpType = PlayerType.SPECIAL;
		} else {
			return combinateDefaultPlayer();
		}

		if (isPercentUp) 
			tmpPercent += PERCENT_UP;
		
		// 성공 혹은 실패 시 결정된 등급 반환
		Function<Integer, PlayerType> typeResult = rand -> (int)(Math.random() * PERCENT) < rand ? tmpType : downType(tmpType);
		return typeResult.apply(tmpPercent);

	}

	private PlayerType downType(PlayerType result) {
		int downType = result.intValue() - 1;
		return downType < 1 ? PlayerType.NORMAL : PlayerType.valueOf(downType);
	}
	
	private PlayerType combinateDefaultPlayer() {
		return PlayerType.valueOf((int) (Math.random() * DEFAULT_PLAYER_TYPE_COUNT) + 1);
	}
	
	private void initMap(Map<PlayerType, Integer> playerTypeMap) {
		playerTypeMap.put(PlayerType.ACE, INIT);
		playerTypeMap.put(PlayerType.MONSTER, INIT);
		playerTypeMap.put(PlayerType.SPECIAL, INIT);
		playerTypeMap.put(PlayerType.NORMAL, INIT);
	}

}
