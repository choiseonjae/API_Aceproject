package com.aceproject.demo.scout.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aceproject.demo.scout.model.ScoutReportOption;
import com.aceproject.demo.scout.model.TeamSrSlotView;
import com.aceproject.demo.scout.service.ScoutReportingService;

@RestController
@RequestMapping("/api/scout")
public class ScoutContorller {

	@Autowired
	private ScoutReportingService srService;

	// GetMapping은 select에 사용하는 것.
	// 해당 메소드는 기본적으로 select의 기능을 하지만 slot이 empty일 경우, 시간이 지남에 따라 자동 갱신을 하는데
	// insert와 update를 사용. GetMapping이 맞는지 질문.
	// 의미상으로 선택
	@GetMapping("/slots")
	public List<TeamSrSlotView> getTeamSrSlotViews(@RequestParam int teamId) {
		return srService.getSrSlots(teamId);
	}

	@PutMapping("/slots/{slotNo}")
	public List<TeamSrSlotView> contract(@RequestParam int teamId, @PathVariable("slotNo") int slotNo) {
		srService.contract(teamId, slotNo);
		return srService.getSrSlots(teamId);
	}

	// defaultType 사용 안한 이유 
	//  -> 기본 값으로 null 과 false가 필요. defaultType은 null이 들어왔을 경우, 설정할 값을 미리 정해놓는 것이기 때문에
	// 따로 defaultType을 하지 않았고, required만 사용 
	@PutMapping("/slots/refresh")
	public List<TeamSrSlotView> apRefresh(@RequestParam int teamId, @RequestParam(name = "teamCode", required = false) String teamCode,
			@RequestParam(name = "costUp", required = false) boolean costUp) {
		ScoutReportOption option = new ScoutReportOption(teamCode, costUp);
		return srService.apRefresh(teamId, option);
	}

}
