package com.aceproject.demo.trade.exception;

public class AlreadyTradedPlayerException extends RuntimeException {

	/**
	 * 재료 player들을 보유하지 않은 상태에서 재료로 사용할 경우 발생
	 * 클라이언트에게 소유하지 않은 player를 보여줄 경우는 없으니, 
	 * 해당 경우는 빠르게 두번 trade를 요청하는 경우를 고려 
	 * 
	 */
	private static final long serialVersionUID = -4466410253417755972L;

	private static final String MSG = "ALREADY_TRADED_PLAYER_EXCEPTION";

	public AlreadyTradedPlayerException() {
		super(MSG);
	}

}
