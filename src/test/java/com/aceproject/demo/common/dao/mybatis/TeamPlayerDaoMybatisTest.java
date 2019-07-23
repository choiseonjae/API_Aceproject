package com.aceproject.demo.common.dao.mybatis;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.aceproject.demo.common.dao.TeamPlayerDao;
import com.aceproject.demo.common.model.TeamPlayer;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TeamPlayerDaoMybatisTest {

	@Autowired
	private TeamPlayerDao teamPlayerDao;

	@Test
	public void test() {

		// 테스트 케이스 작성
		int teamId = 1;

		TeamPlayer teamPlayer = new TeamPlayer(teamId, 1);
		
		TeamPlayer teamPlayer2 = new TeamPlayer(teamId, 2);

		// 입력
		teamPlayerDao.insert(teamPlayer);
		teamPlayerDao.insert(teamPlayer2);

		// 출력
		printTeamPlayer("insert", teamPlayerDao.list(teamId));

		// 수정
		teamPlayer.setLevel(5);
		teamPlayer.setExp(300);
		teamPlayerDao.update(teamPlayer);

		// 출력 
		printTeamPlayer("update", teamPlayerDao.list(teamId));
	}

	private void printTeamPlayer(String comment, List<TeamPlayer> teamPlayers) {
		System.out.println("############ " + comment + " ############");
		System.out.println("팀 id : " + teamPlayers.get(0).getTeamId());

		for (TeamPlayer teamPlayer : teamPlayers) {
			System.out.println("\t############ " + "플레이어 id : " + teamPlayer.getPlayerId() + " ############");
			System.out.println("\tlevel : " + teamPlayer.getLevel());
			System.out.println("\texp : " + teamPlayer.getExp());
			System.out.println("\t생성 날짜 : " + teamPlayer.getCrtDate());
			System.out.println("\t수정 날짜 : " + teamPlayer.getUpdDate());
		}
	}

}
