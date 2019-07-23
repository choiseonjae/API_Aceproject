package com.aceproject.demo.trade.exception;

public class AlreadyTradedPlayerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4466410253417755972L;

	private static final String MSG = "ALREADY_TRADED_PLAYER_EXCEPTION";

	public AlreadyTradedPlayerException() {
		super(MSG);
	}

}
