package com.aceproject.demo.trade;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.aceproject.demo.common.model.Player;
import com.aceproject.demo.trade.model.PlayerType;

public class TradeUtils {

	private static List<PlayerType> types = Arrays.asList(PlayerType.MONSTER, PlayerType.SPECIAL, PlayerType.NORMAL);
	
	
	public static final Function<Player, PlayerType> playerTypeConverter = p -> p.getPlayerType();
	
	public static final Function<Player, Integer> costConverter = p -> p.getCost();


	public static boolean isSuccess(int percent) {
		return (int) (Math.random() * 100) < percent;
	}

	public static PlayerType combinateDefaultPlayer() {
		Collections.shuffle(types);
		return types.get(0);
	}

	// 등급을 세기 위한 자료구조 (map)
	// TODO 굳이 추상클래스를 쓸 필요 없이 default메소드를 통해 기본값을 정의해놓고 선택적으로 오버라이딩을 하는 것이 더좋을듯 하다.
	// 여기도 static util?
	public static <Type, Key> Map<Key, Integer> getCountMap(List<Type> list, Function<Type, Key> convert) {
		return list.stream().collect(
				Collectors.groupingBy(item -> convert.apply(item), Collectors.reducing(0, e -> 1, Integer::sum)));
	}

}
