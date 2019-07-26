package com.aceproject.demo.trade;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.aceproject.demo.trade.model.PlayerType;

public class TradeUtils {
	
	private static List<PlayerType> types = Arrays.asList(PlayerType.MONSTER, PlayerType.SPECIAL, PlayerType.NORMAL);
	
	public static boolean isSuccess(int percent) {
		return (int) (Math.random() *  100) < percent;
	}
	
	public static PlayerType combinateDefaultPlayer() {
		Collections.shuffle(types);
		return types.get(0);
	}
}
