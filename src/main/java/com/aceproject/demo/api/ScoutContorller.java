package com.aceproject.demo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aceproject.demo.model.ScoutReportOption;
import com.aceproject.demo.model.TeamSrSlotView;
import com.aceproject.demo.service.ScoutReportingService;

@RestController
@RequestMapping("/api/scout")
public class ScoutContorller {

	@Autowired
	private ScoutReportingService srService;
	
	@GetMapping("/slots")
	public List<TeamSrSlotView> getTeamSrSlotViews(@RequestParam int teamId){
		return srService.getSrSlots(teamId);
	}
	
	@PutMapping("/slots")
	public List<TeamSrSlotView> contract(@RequestParam int teamId, @RequestParam int slotNo){
		srService.contract(teamId, slotNo);
		return srService.getSrSlots(teamId);
	}
	
	// required 사용 이유 : null 안넣어져서 default 안 쓴 이유
	@PutMapping("/slots/refresh")
	public List<TeamSrSlotView> apRefresh(@RequestParam int teamId, @RequestParam(required = false) String teamCode, @RequestParam(required = false) boolean costUp){
		ScoutReportOption option = new ScoutReportOption(teamCode, costUp);
		return srService.apRefresh(teamId, option);
	} 

}
