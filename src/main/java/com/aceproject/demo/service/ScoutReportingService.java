package com.aceproject.demo.service;

import java.util.List;

import com.aceproject.demo.model.TeamSrSlot;
import com.aceproject.demo.model.TeamSrSlotView;

public interface ScoutReportingService {
	
	void contract(int teamId, int slotNo);
	
	List<TeamSrSlotView> getSrSlots(int teamId);
	
	List<TeamSrSlotView> refresh(int teamId);

}
