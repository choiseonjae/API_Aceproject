
package com.aceproject.demo.trade.model;

public enum PlayerType {

	// TODO 그냥 문자열로 하는 것이 가독성이 좋다
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

	public PlayerType next() {
		switch (this) {
		case NORMAL:
			return SPECIAL;
		case SPECIAL:
			return MONSTER;
		case MONSTER:
			return ACE;
		case ACE:
			return ACE;
		}
		return this;
	}
	
	public static PlayerType min(PlayerType type1, PlayerType type2) {
		return PlayerType.MONSTER.compareTo(type1) > 0 ? type1 : PlayerType.MONSTER;
	}
	

}