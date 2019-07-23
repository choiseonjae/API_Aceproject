package com.aceproject.demo.common.dao.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.aceproject.demo.common.dao.TeamPlayerDao;
import com.aceproject.demo.common.model.TeamPlayer;
import com.aceproject.demo.common.support.CommonDaoSupport;

@Repository
public class TeamPlayerDaoMybatis extends CommonDaoSupport implements TeamPlayerDao {

	@Override
	public void insert(TeamPlayer teamPlayer) {
		getSqlSession().insert("com.aceproject.demo.teamPlayer.insert", teamPlayer);
	}

	@Override
	public void update(TeamPlayer teamPlayer) {
		getSqlSession().update("com.aceproject.demo.teamPlayer.update", teamPlayer);
	}
	
	@Override
	public void delete(int teamId, List<Integer> playerIds) {
		Map<String, Object> params = new HashMap<>();
		params.put("teamId", teamId);
		params.put("playerIds", playerIds);
		getSqlSession().update("com.aceproject.demo.teamPlayer.delete", params);
	}

	@Override
	public List<TeamPlayer> list(int teamId) {
		return getSqlSession().selectList("com.aceproject.demo.teamPlayer.selectList", teamId);
	}

	@Override
	public TeamPlayer get(int teamId, int playerId) {
		Map<String, Object> params = new HashMap<>();
		params.put("teamId", teamId);
		params.put("playerId", playerId);
		return getSqlSession().selectOne("com.aceproject.demo.teamPlayer.select", params);
	}


}
