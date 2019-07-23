package com.aceproject.demo.trade.model;

import java.util.Set;

import com.aceproject.demo.trade.exception.NotEnoughTradeYearException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradeOption {

	private Set<Integer> years;
	
	private static final int MIN_YEAR_COUNT = 2;
	
	public void checkException() {
		if((years != null || !years.isEmpty()) && years.size() < MIN_YEAR_COUNT) {
			throw new NotEnoughTradeYearException();
		}
	}

}