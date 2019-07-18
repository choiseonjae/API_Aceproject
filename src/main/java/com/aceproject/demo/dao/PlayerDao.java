package com.aceproject.demo.dao;

import java.util.List;

import com.aceproject.demo.model.Player;

public interface PlayerDao {

	void insert(Player player);

	void update(Player player);

	Player get(int playerId);

	List<Player> getAll();

}
