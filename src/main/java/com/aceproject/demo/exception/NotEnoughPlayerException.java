package com.aceproject.demo.exception;

public class NotEnoughPlayerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4466410253417755972L;

	private static final String MSG = "NOT_ENOUGH_PLAYER";

	public NotEnoughPlayerException() {
		super(MSG);
	}

}
