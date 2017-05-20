package com.rg.service.bean.rest;

import java.io.Serializable;

public class UserBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private int personId;
	private String name;
	private String result;
	private String userID;
	private String accessToken;
	private MoneyBean money;

	@Override
	public String toString() {
		return "UserBean [personId=" + personId + ", name=" + name + ", result=" + result + ", userID=" + userID
				+ ", accessToken=" + accessToken + "]";
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public MoneyBean getMoney() {
		return money;
	}

	public void setMoney(MoneyBean money) {
		this.money = money;
	}
}
