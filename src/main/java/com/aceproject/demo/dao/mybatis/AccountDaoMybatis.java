package com.aceproject.demo.dao.mybatis;

import org.springframework.stereotype.Repository;

import com.aceproject.demo.dao.AccountDao;
import com.aceproject.demo.model.Account;
import com.aceproject.demo.support.CommonDaoSupport;

@Repository
public class AccountDaoMybatis extends CommonDaoSupport implements AccountDao {

	@Override
	public void insert(Account account) {
		getSqlSession().insert("com.aceproject.demo.account.insert", account);
	}

	@Override
	public void update(Account account) {
		getSqlSession().update("com.aceproject.demo.account.update", account);
	}

	@Override
	public Account get(int teamId) {
		return getSqlSession().selectOne("com.aceproject.demo.account.select", teamId);
	}

}
