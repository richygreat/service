package com.rg.service.business;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.rg.service.dao.UserDAO;
import com.rg.service.entity.Attribute;
import com.rg.service.entity.User;

@Named
@SessionScoped
public class UserService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private UserDAO dao;

	public User getUserByUserID(String userID) {
		return dao.getUserByUserID(userID);
	}

	public List<User> getUsersWithAttribute(Attribute attribute) {
		return dao.getUsersWithAttribute(attribute);
	}

	public List<User> getUsersNotWithAttribute(Attribute attribute) {
		return dao.getUsersNotWithAttribute(attribute);
	}

	public User getUserById(int personId) {
		return dao.getUserById(personId);
	}

	public void deleteUser(int personId) {
		dao.deleteUser(personId);
	}

	public int saveUser(User user) {
		return dao.saveUser(user);
	}

	public void saveUserAttribute(int personId, Attribute attr) {
		dao.saveUserAttribute(personId, attr);
	}

	public void saveUserList(List<User> ls) {
		dao.saveUserList(ls);
	}

	public UserDAO getDao() {
		return dao;
	}

	public void setDao(UserDAO dao) {
		this.dao = dao;
	}
}