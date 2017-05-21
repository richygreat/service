package com.rg.service.bean.rest;

import java.io.Serializable;
import java.util.Date;

public class MoneyBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long moneyId;
	private Double amount;
	private String type;
	private Date date;
	private String description;
	private Boolean credit;
	private String userID;
	private String result;

	@Override
	public String toString() {
		return "MoneyBean [moneyId=" + moneyId + ", amount=" + amount + ", type=" + type + ", date=" + date
				+ ", description=" + description + ", credit=" + credit + ", userID=" + userID + ", result=" + result
				+ "]";
	}

	public Long getMoneyId() {
		return moneyId;
	}

	public void setMoneyId(Long moneyId) {
		this.moneyId = moneyId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getCredit() {
		return credit;
	}

	public void setCredit(Boolean credit) {
		this.credit = credit;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}