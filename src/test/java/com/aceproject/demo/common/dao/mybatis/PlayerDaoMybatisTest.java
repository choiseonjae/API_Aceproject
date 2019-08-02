package com.aceproject.demo.common.dao.mybatis;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Transactional
public class PlayerDaoMybatisTest {
	
//	@Autowired
//	PlayerDao playerDao;
//
//	@Test
//	public void daoTest() throws InterruptedException {
//		
//		List<Player> players = playerDao.getAll();
//		players.forEach(p -> System.out.println(p.getPlayerId()));
//		
//	}
//
//	void printPlayer(String comment, Player player) {
//		System.out.println("############ " + comment + " ############");
//		System.out.println("player id : " + player.getPlayerId());
//		System.out.println("person id : " + player.getPersonId());
//		System.out.println("소속 팀 명 : " + player.getTeamCode());
//		System.out.println("가치 : " + player.getCost());
//		System.out.println("생성 날짜 : " + player.getCrtDate());
//		System.out.println("수정 날짜 : " + player.getUpdDate());
//	}

	@Test
	public void test() {
		List<Integer> tmp = new ArrayList<>();
		tmp.add(1);
		int a = tmp.stream().filter(p -> p == 0).findFirst().get();
//		System.out.println(tmp.stream().filter(p -> p == 0).findFirst().equals(obj));
	}
	
}
