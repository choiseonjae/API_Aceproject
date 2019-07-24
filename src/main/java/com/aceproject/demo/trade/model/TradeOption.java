package com.aceproject.demo.trade.model;

import java.util.Set;

import com.aceproject.demo.trade.exception.NotEnoughTradeYearException;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradeOption {

	private Set<Integer> years;
	private boolean percentUp;
	
	private static final int PERCENT_UP_CASH = 10;
	private static final int MIN_YEAR_COUNT = 2;
	
	public void checkYearException() {
		if(years != null && years.size() < MIN_YEAR_COUNT) {
			throw new NotEnoughTradeYearException();
		}
	}

	public int getDeductCash() {
		return percentUp? PERCENT_UP_CASH : 0;
	}
}