package com.aceproject.demo.scout.dao;

import java.util.List;

import com.aceproject.demo.scout.model.TeamSrSlot;

public interface TeamSrSlotDao {

	void insert(List<TeamSrSlot> teamSrSlots);

	void update(TeamSrSlot teamSrSlot);

	void update(List<TeamSrSlot> teamSrSlots);

	List<TeamSrSlot> list(int teamId);
	
	TeamSrSlot get(int teamId, int slotNo);

}
