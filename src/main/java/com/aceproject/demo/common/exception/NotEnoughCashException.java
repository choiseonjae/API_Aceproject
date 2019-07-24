package com.aceproject.demo.common.exception;

public class NotEnoughCashException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4466410253417755972L;

	private static final String MSG = "NOT_ENOUGH_AP";

	public NotEnoughCashException() {
		super(MSG);
	}

}
