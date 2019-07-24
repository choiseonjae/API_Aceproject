package com.aceproject.demo.common.dao.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Repository;

import com.aceproject.demo.common.dao.PlayerDao;
import com.aceproject.demo.common.model.Player;
import com.aceproject.demo.common.support.CommonDaoSupport;
import com.aceproject.demo.trade.model.PlayerType;

@Repository
public class PlayerDaoMybatis extends CommonDaoSupport implements PlayerDao {

	@Override
	public void insert(Player player) {
		getSqlSession().insert("com.aceproject.demo.player.insert", player);
	}

	@Override
	public void update(Player player) {
		getSqlSession().update("com.aceproject.demo.player.update", player);
	}

	@Override
	public Player get(int playerId) {
		return getSqlSession().selectOne("com.aceproject.demo.player.select", playerId);
	}
	
	@Override
	public List<Player> typeList(PlayerType playerTypeEnum) {
		int playerType = playerTypeEnum.intValue();
		return getSqlSession().selectList("com.aceproject.demo.player.selectTypeList", playerType);
	}
	
	@Override
	public List<Player> yearList(Set<Integer> years) {
		Map<String, Object> params = new HashMap<>();
		params.put("years", years);
		
		return getSqlSession().selectList("com.aceproject.demo.player.selectYearList", params);
	}
	
	@Override
	public List<Player> list(List<Integer> playerIds) {
		Map<String, Object> params = new HashMap<>();
		params.put("playerIds", playerIds);
		return getSqlSession().selectList("com.aceproject.demo.player.selectList", params);
	}
	
	@Override
	public List<Player> getAll() {
		return getSqlSession().selectList("com.aceproject.demo.player.selectAll");
	}




}
