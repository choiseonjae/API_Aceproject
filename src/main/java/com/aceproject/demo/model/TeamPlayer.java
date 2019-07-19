package com.aceproject.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamPlayer extends Dto {
	private int teamId;
	private int playerId;
	private int level;
	private int exp;

	public TeamPlayer(int teamId, int playerId, int level, int exp) {
		super();
		this.teamId = teamId;
		this.playerId = playerId;
		this.level = level;
		this.exp = exp;
	}
	
	public void levelUp() {
		level += 1;
	}

}
