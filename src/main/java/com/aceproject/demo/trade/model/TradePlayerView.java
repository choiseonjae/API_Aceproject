package com.aceproject.demo.trade.model;

import com.aceproject.demo.common.model.Person;
import com.aceproject.demo.common.model.Player;

import lombok.Setter;

@Setter
public class TradePlayerView {

	private Person person;
	private Player player;

	public int getPlayerId() {
		return player.getPlayerId();
	}

	public int getPersonId() {
		return player.getPersonId();
	}

	public String getName() {
		return person.getName();
	}

	public String getTeamCode() {
		return player.getTeamCode();
	}

	public int getCost() {
		return player.getCost();
	}

}
