package com.aceproject.demo.exception;

public class AlreadyContractPlayerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4466410253417755972L;

	private static final String MSG = "ALREADY_CONTRACT_PLAYER";

	public AlreadyContractPlayerException() {
		super(MSG);
	}

}
