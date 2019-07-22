package com.aceproject.demo.dao.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.aceproject.demo.dao.PersonDao;
import com.aceproject.demo.model.Person;
import com.aceproject.demo.support.CommonDaoSupport;

@Repository
public class PersonDaoMybatis extends CommonDaoSupport implements PersonDao {

	@Override
	public void insert(Person person) {
		getSqlSession().insert("com.aceproject.demo.person.insert", person);
	}

	@Override
	public void update(Person person) {
		getSqlSession().update("com.aceproject.demo.person.update", person);

	}

	@Override
	public Person get(int personId) {
		return getSqlSession().selectOne("com.aceproject.demo.person.select", personId);

	}

	@Override
	public List<Person> list(List<Integer> personIds) {
		Map<String, Object> params = new HashMap<>();
		params.put("personIds", personIds);
		return getSqlSession().selectList("com.aceproject.demo.person.selectList", params);
	}

	@Override
	public List<Person> getAll() {
		return getSqlSession().selectList("com.aceproject.demo.person.selectAll");

	}

}
