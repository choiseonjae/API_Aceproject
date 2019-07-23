package com.aceproject.demo.common.dao;

import java.util.List;

import com.aceproject.demo.common.model.TeamPlayer;

public interface TeamPlayerDao {
	
	void insert(TeamPlayer teamPlayer);
	
	void update(TeamPlayer teamPlayer);
	
	void delete(int teamId, List<Integer> playerIds);
	
	List<TeamPlayer> list(int teamId);
	
	TeamPlayer get(int teamId, int playerId);

}
