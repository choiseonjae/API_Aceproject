package com.aceproject.demo.common.model;

import com.aceproject.demo.trade.model.PlayerType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Player extends Dto {
	private int playerId;
	private int personId;
	private String teamCode;
	private int cost;
	private int year;
	private PlayerType playerType;
	
	public Player(PlayerType type) {
		playerType = type;
	}
}
