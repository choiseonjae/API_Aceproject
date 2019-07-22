package com.aceproject.demo.dao;

import java.util.List;

import com.aceproject.demo.model.Person;

public interface PersonDao {

	void insert(Person person);

	void update(Person person);

	Person get(int personId);
	
	List<Person> list(List<Integer> personIds);

	List<Person> getAll();

}
