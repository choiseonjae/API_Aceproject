package com.aceproject.demo.common.service;

public interface AccountService {
	
	/**
	 * ap 차감 및 예외 처리
	 * 
	 * @param teamId
	 * @param deductAp
	 */
	void deductAp(int teamId, int deductAp);

	void addAp(int teamId, int addAp);

	void deductCash(int teamId, int deductCash);
}
