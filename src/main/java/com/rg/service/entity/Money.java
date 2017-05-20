package com.rg.service.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Entity implementation class for Entity: Money
 *
 */
@Entity
public class Money implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long moneyId;
	private Double amount;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Type type;
	private Date date;
	private String description;
	private Boolean credit;

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "Money [moneyId=" + moneyId + ", amount=" + amount + ", type=" + type + ", date=" + date
				+ ", description=" + description + ", credit=" + credit + "]";
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

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
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

}