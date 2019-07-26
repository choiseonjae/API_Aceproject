package com.aceproject.demo.trade.exception;

public class NotEnoughConditionPlayerException extends RuntimeException {

	/**
	 * trade 요청 시 설정한 Option을 적용 후 새로 영입 가능한 player가 존재하지 않을 경우 발생
	 * 
	 */
	private static final long serialVersionUID = -4466410253417755972L;

	private static final String MSG = "NOT_ENOUGH_CONDITION_PLAYER_EXCEPTION";

	public NotEnoughConditionPlayerException() {
		super(MSG);
	}

}
