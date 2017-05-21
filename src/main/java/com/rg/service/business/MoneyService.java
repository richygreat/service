package com.rg.service.business;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.rg.service.dao.MoneyDAO;
import com.rg.service.entity.Money;

@Named
@SessionScoped
public class MoneyService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private MoneyDAO dao;

	public Long saveMoney(Money money) {
		return dao.saveMoney(money);
	}

	public MoneyDAO getDao() {
		return dao;
	}

	public void setDao(MoneyDAO dao) {
		this.dao = dao;
	}
}