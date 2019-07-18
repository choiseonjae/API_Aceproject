package com.aceproject.demo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeamSrSlot extends Dto {
	private int teamId;
	private int slotNo;
	private int playerId;
	private YN contractYN;

	public TeamSrSlot(int teamId, int slotNo, int playerId, YN contractYN) {
		super();
		this.teamId = teamId;
		this.slotNo = slotNo;
		this.playerId = playerId;
		this.contractYN = contractYN;
	}

}
