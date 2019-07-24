package com.aceproject.demo.common.dao.mybatis;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.aceproject.demo.common.dao.AccountDao;
import com.aceproject.demo.common.model.Account;
import com.aceproject.demo.common.support.CommonDaoSupport;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AccountDaoMybatisTest extends CommonDaoSupport {

	@Autowired
	private AccountDao accountDao;

	@Test
	public void test() {

		// 테스트 케이스 작성
		int teamId = 1;
		int ap = 1000;

		Account account = new Account();
		account.setTeamId(teamId);
		account.setAp(ap);

		// 입력
		accountDao.insert(account);

		// 출력
		printAccount("insert", accountDao.get(teamId));

		// 수정
		account.setAp(ap - 50);
		accountDao.updateAp(account);

		// 출력
		printAccount("update", accountDao.get(teamId));

	}

	private void printAccount(String comment, Account account) {
		System.out.println("############ " + comment + " ############");
		System.out.println("팀 아이디 : " + account.getTeamId());
		System.out.println("팀 ap : " + account.getAp());
		System.out.println("생성 날짜 : " + account.getCrtDate());
		System.out.println("수정 날짜 : " + account.getUpdDate());
	}

}
