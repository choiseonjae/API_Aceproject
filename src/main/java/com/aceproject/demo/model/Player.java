package com.aceproject.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player extends Dto {
	private int playerId;
	private int personId;
	private String teamCode;
	private int cost;
}
