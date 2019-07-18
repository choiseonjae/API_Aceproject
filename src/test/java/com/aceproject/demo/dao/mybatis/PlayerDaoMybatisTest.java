package com.aceproject.demo.dao.mybatis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.aceproject.demo.dao.PlayerDao;
import com.aceproject.demo.model.Player;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PlayerDaoMybatisTest {

	@Autowired
	private PlayerDao playerDao;

	@Test
	public void daoTest() throws InterruptedException {

		int playerId = 14;
		int cost = 50;

		// test case
		Player player = new Player();

		player.setPlayerId(playerId);
		player.setPersonId(1);
		player.setTeamCode("한화 이글스");
		player.setCost(cost);

		playerDao.insert(player);

		printPlayer("insert", playerDao.get(playerId));

		player.setTeamCode("롯데 자이언트");
		playerDao.update(player);

		printPlayer("update", playerDao.get(playerId));

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
