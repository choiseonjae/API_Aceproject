package com.aceproject.demo.dao.mybatis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.aceproject.demo.dao.TeamSrSlotDao;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TeamSrSlotDaoMybatisTest {

	@Autowired
	private TeamSrSlotDao teamSrSlotDao;
//
//	@Test
//	public void daoTest() throws InterruptedException {
//		
//		int teamId = 1;
//		int maxSlot = 4;
//		
//		List<TeamSrSlot> teamSrSlots = new ArrayList<>();
//
//		// 입력
//		for(int i = 1; i <= maxSlot; i++) {
//			TeamSrSlot teamSrSlot = new TeamSrSlot(teamId, i, i);
//			teamSrSlot.setContractYN(YN.N);
//			teamSrSlots.add(teamSrSlot);
//		}
//		teamSrSlotDao.insert(teamSrSlots);
//
//		// 출력
//		printScoutPlayers("insert", teamSrSlotDao.list(teamId));
//		
//		Thread.sleep(1000);
//		
//		// 수정
//		for(int i = 1; i <= maxSlot; i++) {
//			teamSrSlots.get(i).setContractYN(YN.Y);
//		}
//		teamSrSlotDao.update(teamSrSlots);
//
//		printScoutPlayers("영입 후 slot 상태", teamSrSlotDao.list(1));
//
//	}

//	private void printScoutPlayers(String comment, List<TeamSrSlot> players) {
//		System.out.println("팀 아이디 : " + players.get(0).getTeamId());
//		System.out.println("############ " + comment + " ############");
//		for (TeamSrSlot player : players) {
//			System.out.println("\t############ " + "슬롯 번호 : " + player.getSlotNo() + " ############");
//			System.out.println("\t플레이어 아이디 : " + player.getPlayerId());
//			System.out.println("\t영입 여부 : " + player.getContractYN());
//			System.out.println("\t생성 날짜 : " + player.getCrtDate());
//			System.out.println("\t수정 날짜 : " + player.getUpdDate());
//			System.out.println();
//		}
//
//	}

	@Test
	public void test_get() {
		int teamId = 1;

		teamSrSlotDao.list(teamId).forEach(System.out::println);
	}
}
