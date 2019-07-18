package com.aceproject.demo.dao;

import com.aceproject.demo.model.Account;

public interface AccountDao {
	
	void insert(Account account);
	
	void update(Account account);
	
	Account get(int teamId);

}
