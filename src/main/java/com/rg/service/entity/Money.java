package com.rg.service.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
	private Double interestRate;
	private Double emi;
	private int instMonths;
	private int remainInstMonths;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Type type;
	private Date date;
	private Date amortStartDate;
	private String description;
	private Boolean credit;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "personId")
	private User user;

	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "Money [moneyId=" + moneyId + ", amount=" + amount + ", interestRate=" + interestRate + ", emi=" + emi
				+ ", instMonths=" + instMonths + ", remainInstMonths=" + remainInstMonths + ", type=" + type + ", date="
				+ date + ", amortStartDate=" + amortStartDate + ", description=" + description + ", credit=" + credit
				+ ", user=" + user + "]";
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(Double interestRate) {
		this.interestRate = interestRate;
	}

	public Date getAmortStartDate() {
		return amortStartDate;
	}

	public void setAmortStartDate(Date amortStartDate) {
		this.amortStartDate = amortStartDate;
	}

	public Double getEmi() {
		if (emi == null) {
			emi = getEmiAmount(this);
		}
		return emi;
	}

	public void setEmi(Double emi) {
		this.emi = emi;
	}

	public int getInstMonths() {
		return instMonths;
	}

	public void setInstMonths(int instMonths) {
		this.instMonths = instMonths;
	}

	public int getRemainInstMonths() {
		return remainInstMonths;
	}

	public void setRemainInstMonths(int remainInstMonths) {
		this.remainInstMonths = remainInstMonths;
	}

	public static double getEmiAmount(Money money) {
		return Math.round(money.getAmount() * (money.getInterestRate() / 1200)
				/ (1 - (1 / Math.pow(1 + (money.getInterestRate() / 1200), money.getInstMonths()))));
	}

	public static double getBalanceAmount(Money money) {
		return Math.round(
				money.getEmi() * (1 - (1 / Math.pow(1 + (money.getInterestRate() / 1200), money.getRemainInstMonths())))
						/ (money.getInterestRate() / 1200));
	}
}