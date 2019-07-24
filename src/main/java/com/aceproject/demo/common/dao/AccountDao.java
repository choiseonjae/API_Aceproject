package com.aceproject.demo.common.dao;

import com.aceproject.demo.common.model.Account;

public interface AccountDao {
	
	void insert(Account account);
	
	void updateAp(Account account);
	
	void updateCash(Account account);
	
	Account get(int teamId);

}
