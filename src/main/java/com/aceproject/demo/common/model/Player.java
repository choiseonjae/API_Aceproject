package com.aceproject.demo.common.model;

import com.aceproject.demo.trade.model.PlayerType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player extends Dto {
	private int playerId;
	private int personId;
	private String teamCode;
	private int cost;
	private int year;
	private PlayerType playerType;
	
	public void setPlayerType(int playerType) {
		this.playerType = PlayerType.valueOf(playerType);
	}
}
