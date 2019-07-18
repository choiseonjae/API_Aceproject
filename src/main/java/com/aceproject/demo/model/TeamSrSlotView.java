package com.aceproject.demo.model;

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
