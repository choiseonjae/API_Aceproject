package com.aceproject.demo.trade.exception;

public class NotEnoughTradeYearException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4466410253417755972L;

	private static final String MSG = "NOT_ENOUGH_TRADE_YEAR_EXCEPTION";

	public NotEnoughTradeYearException() {
		super(MSG);
	}

}
