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
	private boolean costUp;
	
	private static final int UP_CASH = 10;
	private static final int MIN_YEAR_COUNT = 2;
	private static final int MAX_COST_AVG = 8;
	
	public TradeOption(Set<Integer> years, boolean percentUp, boolean costUp) {
		this.years = years;
		this.percentUp = percentUp;
		this.costUp = costUp;
	}
	
	public void checkYearException() {
		if(years != null && years.size() < MIN_YEAR_COUNT) {
			throw new NotEnoughTradeYearException();
		}
	}

	public int getDeductCash() {
		int cash = 0;
		if(percentUp)
			cash += UP_CASH;
		if(costUp)
			cash += UP_CASH;
		return cash;
	}
	
	public static int costUp(int cost) {
		return Math.min(cost + 1, MAX_COST_AVG);
	}
}