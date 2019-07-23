package com.aceproject.demo.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aceproject.demo.common.dao.AccountDao;
import com.aceproject.demo.common.exception.NotEnoughApException;
import com.aceproject.demo.common.model.Account;
import com.aceproject.demo.common.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountDao accountDao;

	@Override
	// ap 차감 및 예외 처리
	public void deductAp(int teamId, int deductAp) {

		// account 잔고 조회
		Account account = accountDao.get(teamId);

		// 잔고가 부족하면 예외
		if (deductAp > account.getAp())
			throw new NotEnoughApException();

		// 잔고가 부족하지 않으면 ap 차감
		account.setAp(account.getAp() - deductAp);
		accountDao.update(account);
	}

	@Override
	public void addAp(int teamId, int addAp) {
		Account account = accountDao.get(teamId);
		
		account.setAp(account.getAp() - addAp);
		accountDao.update(account);
	}

}
