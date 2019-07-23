package com.aceproject.demo.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamPlayer extends Dto {
	private int teamId;
	private int playerId;
	private int level;
	private int exp;

	private static final int INIT_LEVEL = 1;
	private static final int INIT_EXP = 1;

	public TeamPlayer() {
	}

	public TeamPlayer(int teamId, int playerId) {
		super();
		this.teamId = teamId;
		this.playerId = playerId;
		level = INIT_LEVEL;
		exp = INIT_EXP;
	}

	public void levelUp() {
		level += 1;
	}

}
