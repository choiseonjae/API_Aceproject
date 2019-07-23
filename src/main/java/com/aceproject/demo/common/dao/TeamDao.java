package com.aceproject.demo.common.dao;

import com.aceproject.demo.common.model.Team;

public interface TeamDao {

	void insert(Team team);

	void update(Team team);

	Team get(int teamId);

}
