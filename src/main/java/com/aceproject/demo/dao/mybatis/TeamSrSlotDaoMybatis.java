package com.aceproject.demo.dao.mybatis;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.aceproject.demo.dao.TeamSrSlotDao;
import com.aceproject.demo.model.TeamSrSlot;
import com.aceproject.demo.support.CommonDaoSupport;

@Repository
public class TeamSrSlotDaoMybatis extends CommonDaoSupport implements TeamSrSlotDao {

	@Override
	public void insert(List<TeamSrSlot> teamSrSlots) {
		if (teamSrSlots.isEmpty())
			return;

		teamSrSlots.stream().forEach(s -> getSqlSession().insert("com.aceproject.demo.teamSrSlot.insert", s));
	}

	@Override
	public void update(TeamSrSlot teamSrSlot) {
		getSqlSession().update("com.aceproject.demo.teamSrSlot.update", teamSrSlot);
	}

	@Override
	public void update(List<TeamSrSlot> teamSrSlots) {
		if (teamSrSlots.isEmpty())
			return;
		
		teamSrSlots.stream().forEach(s -> getSqlSession().update("com.aceproject.demo.teamSrSlot.update", s));
	}

	public List<TeamSrSlot> list(int teamId) {
		return getSqlSession().selectList("com.aceproject.demo.teamSrSlot.selectList", teamId);
	}

}
