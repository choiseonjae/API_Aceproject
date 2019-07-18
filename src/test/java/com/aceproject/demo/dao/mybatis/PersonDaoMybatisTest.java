package com.aceproject.demo.dao.mybatis;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.aceproject.demo.dao.PersonDao;
import com.aceproject.demo.model.Person;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PersonDaoMybatisTest {

	@Autowired
	private PersonDao personDao;

	@Test
	public void daoTest() throws InterruptedException {
		
		int personId = 1000;
		String name = "최선재";
		
		// test case
		Person person = new Person();
		
		person.setPersonId(personId);
		person.setName(name);
		
		// insert person
		personDao.insert(person);

		// select person && compare value (= 값비교 + 콘솔에 출력)
		printPerson("insert", personDao.get(personId));
		assertEquals(personDao.get(personId).getPersonId(), person.getPersonId());
		assertEquals(personDao.get(personId).getName(), person.getName());
		
		// update person
		person.setName("김태균");
		personDao.update(person);

		// select person && compare value (= 값비교 + 콘솔에 출력)
		printPerson("update", personDao.get(personId));
		assertEquals(personDao.get(personId).getPersonId(), person.getPersonId());
		assertEquals(personDao.get(personId).getName(), person.getName());


	}

	void printPerson(String comment, Person person) {
		System.out.println("############ " + comment + " ############");
		System.out.println("person id : " + person.getPersonId());
		System.out.println("이름 : " + person.getName());
		System.out.println("생성 날짜 : " + person.getCrtDate());
		System.out.println("수정 날짜 : " + person.getUpdDate());
	}

}
