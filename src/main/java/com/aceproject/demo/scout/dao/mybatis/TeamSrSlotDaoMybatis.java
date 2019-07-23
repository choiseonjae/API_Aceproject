package com.aceproject.demo.scout.dao.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.aceproject.demo.common.support.CommonDaoSupport;
import com.aceproject.demo.scout.dao.TeamSrSlotDao;
import com.aceproject.demo.scout.model.TeamSrSlot;

@Repository
public class TeamSrSlotDaoMybatis extends CommonDaoSupport implements TeamSrSlotDao {

	@Override
	public void insert(List<TeamSrSlot> teamSrSlots) {
		if (teamSrSlots.isEmpty())
			return;

		teamSrSlots.stream().forEach(s -> getSqlSession().insert("com.aceproject.demo.scout.teamSrSlot.insert", s));
	}

	@Override
	public void update(TeamSrSlot teamSrSlot) {
		getSqlSession().update("com.aceproject.demo.scout.teamSrSlot.update", teamSrSlot);
	}

	@Override
	public void update(List<TeamSrSlot> teamSrSlots) {
		if (teamSrSlots.isEmpty())
			return;
		
		teamSrSlots.stream().forEach(s -> getSqlSession().update("com.aceproject.demo.scout.teamSrSlot.update", s));
	}

	@Override
	public List<TeamSrSlot> list(int teamId) {
		return getSqlSession().selectList("com.aceproject.demo.scout.teamSrSlot.selectList", teamId);
	}
	
	@Override
	public TeamSrSlot get(int teamId, int slotNo) {
		Map<String, Object> params = new HashMap<>();
		params.put("teamId", teamId);
		params.put("slotNo", slotNo);
		return getSqlSession().selectOne("com.aceproject.demo.scout.teamSrSlot.select", params);
	}

}
