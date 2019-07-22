package com.aceproject.demo.dao.mybatis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.aceproject.demo.model.Player;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PlayerDaoMybatisTest {

	@Test
	public void daoTest() throws InterruptedException {
		
	}

	void printPlayer(String comment, Player player) {
		System.out.println("############ " + comment + " ############");
		System.out.println("player id : " + player.getPlayerId());
		System.out.println("person id : " + player.getPersonId());
		System.out.println("소속 팀 명 : " + player.getTeamCode());
		System.out.println("가치 : " + player.getCost());
		System.out.println("생성 날짜 : " + player.getCrtDate());
		System.out.println("수정 날짜 : " + player.getUpdDate());
	}

}
