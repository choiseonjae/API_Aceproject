package com.aceproject.demo.trade.model;

public enum PlayerType {

	NORMAL(1), SPECIAL(2), MONSTER(3), ACE(4);

	private final int value;

	PlayerType(int value) {
		this.value = value;
	}

	public int intValue() {
		return value;
	}

	public static PlayerType valueOf(int value) {
		switch (value) {
		case 1:
			return NORMAL;
		case 2:
			return SPECIAL;
		case 3:
			return MONSTER;
		case 4:
			return ACE;

		default:
			throw new AssertionError("unknow value  :" + value);
		}
	}

}