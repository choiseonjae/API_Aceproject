package com.aceproject.demo.trade.exception;

public class NotEnoughConditionPlayerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4466410253417755972L;

	private static final String MSG = "NOT_ENOUGH_CONDITION_PLAYER_EXCEPTION";

	public NotEnoughConditionPlayerException() {
		super(MSG);
	}

}
