package com.aceproject.demo.dao;

import java.util.List;

import com.aceproject.demo.model.TeamSrSlot;

public interface TeamSrSlotDao {

	void insert(List<TeamSrSlot> teamSrSlots);

	void update(TeamSrSlot teamSrSlot);

	void update(List<TeamSrSlot> teamSrSlots);

	List<TeamSrSlot> list(int teamId);

}
