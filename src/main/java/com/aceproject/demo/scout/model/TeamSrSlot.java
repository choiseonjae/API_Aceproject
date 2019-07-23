package com.aceproject.demo.scout.model;

import com.aceproject.demo.common.model.Dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
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
