package com.aceproject.demo.dao.mybatis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.aceproject.demo.dao.TeamDao;
import com.aceproject.demo.model.Team;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class TeamDaoMybatisTest {

	@Autowired
	private TeamDao teamDaoMybatis;

	@Test
	public void test() throws InterruptedException {

		// 넣어볼 team_id
		final int teamId = 999;
		
		// 초기 입력 데이터 초기화
		Team team = new Team();
		team.setTeamId(teamId);
		team.setTeamName("에이스 프로젝트");

		// 입력 
		teamDaoMybatis.insert(team);

		// 출력 
		printTeam("insert 출력", teamDaoMybatis.get(teamId));
		
		// 수정 시간 업데이트 확인
		Thread.sleep(1000);

		// 수정 
		team.setTeamName("컴투스");
		teamDaoMybatis.update(team);

		// 출력 
		printTeam("update 출력", teamDaoMybatis.get(teamId));

	}

	private void printTeam(String comment, Team team) {
		System.out.println("############ " + comment + " ############");
		System.out.println("팀 id : " + team.getTeamId());
		System.out.println("팀 이름 : " + team.getTeamName());
		System.out.println("생성 날짜 : " + team.getCrtDate());
		System.out.println("수정 날짜 : " + team.getUpdDate());
	}

}
