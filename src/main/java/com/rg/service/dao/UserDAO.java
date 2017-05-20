package com.rg.service.dao;

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

import com.rg.service.entity.Attribute;
import com.rg.service.entity.User;

@Named
@SessionScoped
public class UserDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(UserDAO.class.getName());

	@PersistenceContext(unitName = "servicedb")
	private EntityManager entityManager;

	public UserDAO() {
		log.info("UserDAO Constructor called");
	}

	@Transactional
	public List<User> getUsersWithAttribute(Attribute attribute) {
		log.info("Entering getUsersWithAttribute");
		List<User> users = entityManager
				.createQuery("select u from User u where :attribute in elements(u.attributes)", User.class)
				.setParameter("attribute", attribute).getResultList();
		log.info("Exiting getUsersWithAttribute");
		return users;
	}

	@Transactional
	public List<User> getUsersNotWithAttribute(Attribute attribute) {
		log.info("Entering getUsersNotWithAttribute");
		List<User> users = entityManager
				.createQuery("select u from User u where :attribute not in elements(u.attributes)", User.class)
				.setParameter("attribute", attribute).getResultList();
		log.info("Exiting getUsersNotWithAttribute");
		return users;
	}

	@Transactional
	public User getUserByUserID(String userID) {
		log.info("Entering getUserByUserID");
		User result = null;
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> cq = cb.createQuery(User.class);
		Root<User> root = cq.from(User.class);
		cq.where(cb.equal(root.get("userID"), userID));
		TypedQuery<User> query = entityManager.createQuery(cq);
		try {
			result = query.getSingleResult();
		} catch (NoResultException e) {
			log.info("No Result found");
		} catch (NonUniqueResultException e) {
			log.info("Multiple Results found");
		}
		log.info("Exiting getUserByUserID");
		return result;
	}

	@Transactional
	public User getUserById(int personId) {
		log.info("Entering getUserById");
		User user = entityManager.find(User.class, personId);
		log.info("Exiting getUserById");
		return user;
	}

	@Transactional
	public void deleteUser(int personId) {
		log.info("Entering deleteUser");
		User user = entityManager.find(User.class, personId);
		entityManager.remove(user);
		log.info("Entering deleteUser");
	}

	@Transactional
	public int saveUser(User user) {
		log.info("Entering saveUser");
		log.info("Persisting :: " + user);
		if (user.getPersonId() == 0) {
			entityManager.persist(user);
		} else {
			entityManager.merge(user);
		}
		log.info("Exiting saveUser");
		log.info("person id" + user.getPersonId());
		return user.getPersonId();
	}

	@Transactional
	public void saveUserAttribute(int personId, Attribute attr) {
		log.info("Entering saveUserAttribute");
		User user = entityManager.find(User.class, personId);
		log.info("Updating :: " + user);
		if (attr.getAttributeId() != 0) {
			Attribute oldAttr = entityManager.find(Attribute.class, attr.getAttributeId());
			log.info("Found Attribute :: " + oldAttr);
			oldAttr.setValue(attr.getValue());
			entityManager.merge(oldAttr);
		}else {
			log.info("New Attribute :: " + attr);
			user.getAttributes().add(attr);
			entityManager.merge(user);
		}
		log.info("Exiting saveUserAttribute");
	}

	@Transactional
	public void saveUserList(List<User> ls) {
		log.info("Entering saveUserList");
		for (User user : ls) {
			saveUser(user);
		}
		log.info("Exiting saveUserList");
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
