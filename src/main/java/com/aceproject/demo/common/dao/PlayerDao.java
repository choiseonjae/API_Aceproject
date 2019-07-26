package com.aceproject.demo.common.dao;

import java.util.List;
import java.util.Set;

import com.aceproject.demo.common.model.Player;
import com.aceproject.demo.trade.model.PlayerType;

public interface PlayerDao {

	void insert(Player player);

	void update(Player player);

	Player get(int playerId);
	
	List<Player> yearList(Set<Integer> years);
	
	List<Player> list(List<Integer> playerIds);
	
	List<Player> getAll();

}
