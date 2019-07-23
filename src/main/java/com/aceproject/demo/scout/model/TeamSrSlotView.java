package com.aceproject.demo.scout.model;

import com.aceproject.demo.common.model.Person;
import com.aceproject.demo.common.model.Player;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamSrSlotView {

	// playerId, teamCode, name, slotNo....
	private TeamSrSlot teamSrSlot;
	private Player player;
	private Person person;

	public TeamSrSlotView(TeamSrSlot teamSrSlot, Player player, Person person) {
		super();
		this.teamSrSlot = teamSrSlot;
		this.player = player;
		this.person = person;
	}


}
