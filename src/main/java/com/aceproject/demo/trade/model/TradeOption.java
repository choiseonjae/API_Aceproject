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
	
	// cost up 시 지불해야 하는 ap
	private static final int COST_UP_AP= 100;
	// percent up 시 지불해야 하는 cash
	private static final int PERCENT_UP_CASH = 10;
	// 증가 확률
	private static final int PERCENT_UP = 10;
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

	public int getPercentCash() {
		return percentUp ? PERCENT_UP_CASH : 0;
	}
	
	public int getCostUpAP() {
		return costUp ? COST_UP_AP : 0;
	}
	
	public static int costUp(int cost) {
		return Math.min(cost + 1, MAX_COST_AVG);
	}
	

	public int getPercent(int percent) {
		return percentUp ? percent + PERCENT_UP : percent;
	}
}