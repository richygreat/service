package com.occar.service;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.occar.dao.PersonDAO;
import com.occar.entity.Person;
import com.rg.service.entity.Attribute;

@Named
@SessionScoped
public class PersonService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private PersonDAO dao;
	
	public Person getPersonByUserID(String userID) {
		return dao.getPersonByUserID(userID);
	}
	
	public List<Person> getPersonsWithAttribute(Attribute attribute) {
		return dao.getPersonsWithAttribute(attribute);
	}
	
	public List<Person> getPersonsNotWithAttribute(Attribute attribute) {
		return dao.getPersonsNotWithAttribute(attribute);
	}
	
	public Person getPersonById(int personId) {
		return dao.getPersonById(personId);
	}
	
	public void deletePerson(int personId) {
		dao.deletePerson(personId);
	}
	
	public int savePerson(Person person) {
		return dao.savePerson(person);
	}
	
	public void savePersonAttribute(int personId, Attribute attr) {
		dao.savePersonAttribute(personId, attr);
	}
	
	public void savePersonList(List<Person> ls) {
		dao.savePersonList(ls);
	}

	public PersonDAO getDao() {
		return dao;
	}

	public void setDao(PersonDAO dao) {
		this.dao = dao;
	}
}