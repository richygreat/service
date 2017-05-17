package com.occar.dao;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import com.occar.entity.Attribute;
import com.occar.entity.Person;

@Named
@SessionScoped
public class PersonDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(PersonDAO.class.getName());

	@PersistenceContext(unitName = "occardb")
	private EntityManager entityManager;

	public PersonDAO() {
		log.info("PersonDAO Constructor called");
	}

	@Transactional
	public List<Person> getPersonsWithAttribute(Attribute attribute) {
		log.info("Entering getPersonsWithAttribute");
		List<Person> persons = entityManager
				.createQuery("select p from Person p where :attribute in elements(p.attributes)", Person.class)
				.setParameter("attribute", attribute).getResultList();
		log.info("Exiting getPersonsWithAttribute");
		return persons;
	}

	@Transactional
	public List<Person> getPersonsNotWithAttribute(Attribute attribute) {
		log.info("Entering getPersonsNotWithAttribute");
		List<Person> persons = entityManager
				.createQuery("select p from Person p where :attribute not in elements(p.attributes)", Person.class)
				.setParameter("attribute", attribute).getResultList();
		log.info("Exiting getPersonsNotWithAttribute");
		return persons;
	}

	@Transactional
	public Person getPersonByUserID(String userID) {
		log.info("Entering getPersonByUserID");
		Person result = null;
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Person> cq = cb.createQuery(Person.class);
		Root<Person> root = cq.from(Person.class);
		cq.where(cb.equal(root.get("userID"), userID));
		TypedQuery<Person> query = entityManager.createQuery(cq);
		try {
			result = query.getSingleResult();
		} catch (NoResultException e) {
			log.info("No Result found");
		} catch (NonUniqueResultException e) {
			log.info("Multiple Results found");
		}
		log.info("Exiting getPersonByUserID");
		return result;
	}

	@Transactional
	public Person getPersonById(int personId) {
		log.info("Entering getPersonById");
		Person person = entityManager.find(Person.class, personId);
		log.info("Exiting getPersonById");
		return person;
	}

	@Transactional
	public void deletePerson(int personId) {
		log.info("Entering deletePerson");
		Person person = entityManager.find(Person.class, personId);
		entityManager.remove(person);
		log.info("Entering deletePerson");
	}

	@Transactional
	public int savePerson(Person person) {
		log.info("Entering savePerson");
		log.info("Persisting :: " + person);
		if (person.getPersonId() == 0) {
			entityManager.persist(person);
		} else {
			entityManager.merge(person);
		}
		log.info("Exiting savePerson");
		log.info("person id" + person.getPersonId());
		return person.getPersonId();
	}

	@Transactional
	public void savePersonAttribute(int personId, Attribute attr) {
		log.info("Entering savePersonAttribute");
		Person person = entityManager.find(Person.class, personId);
		log.info("Updating :: " + person);
		if (attr.getAttributeId() != 0) {
			Attribute oldAttr = entityManager.find(Attribute.class, attr.getAttributeId());
			log.info("Found Attribute :: " + oldAttr);
			oldAttr.setValue(attr.getValue());
			entityManager.merge(oldAttr);
		}else {
			log.info("New Attribute :: " + attr);
			person.getAttributes().add(attr);
			entityManager.merge(person);
		}
		log.info("Exiting savePersonAttribute");
	}

	@Transactional
	public void savePersonList(List<Person> ls) {
		log.info("Entering savePersonList");
		for (Person person : ls) {
			savePerson(person);
		}
		log.info("Exiting savePersonList");
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
