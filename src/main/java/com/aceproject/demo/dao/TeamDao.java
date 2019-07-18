package com.aceproject.demo.dao;

import com.aceproject.demo.model.Team;

public interface TeamDao {

	void insert(Team team);

	void update(Team team);

	Team get(int teamId);

}
