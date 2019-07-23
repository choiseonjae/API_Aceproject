package com.aceproject.demo.scout.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ScoutReportingServiceImplTest {

//	@Autowired
//	private ScoutReportingService scoutReportingService;
//
//	private static final int teamId = 1;
	
	@Test
	@Rollback(false)
	public void test() {


//		// getSrSlot Test
//		getSrSlotTest("========= getSrSlot Test =========");
//		
//		// refresh Test
//		refreshTest("========= refresh test =========");
//		
////		// contract Test
//		int slotNo = 3;
//		scoutReportingService.contract(teamId, slotNo);
//		System.out.printf("========= %d 슬롯 player에 대해 contract Test =========\n", slotNo);
//		printSrSlotViews(scoutReportingService.getSrSlots(teamId));

	}
	
//	private void getSrSlotTest(String comment) {
//		System.out.println(comment);
//		printSrSlotViews(scoutReportingService.getSrSlots(teamId));
//		
//	}
//
//	private void refreshTest(String comment) {
//		System.out.println(comment);
//		printSrSlotViews(scoutReportingService.refresh(teamId));
//
//	}
//	
//	private void printSrSlotViews(List<TeamSrSlotView> srSlotViews) {
//		srSlotViews.stream().forEach( s -> {
//			System.out.println("slotNO : " + s.getTeamSrSlot().getSlotNo());
//			System.out.println("teamCode : " + s.getPlayer().getTeamCode());
//			System.out.println("player Name : " + s.getPerson().getName());
//			System.out.println("contract : " + s.getTeamSrSlot().getContractYN());
// 	 	});
//	}

}
