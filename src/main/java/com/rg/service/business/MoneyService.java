package com.rg.service.business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.rg.service.constant.CommonConstants;
import com.rg.service.dao.CommonDAO;
import com.rg.service.dao.MoneyDAO;
import com.rg.service.entity.Money;
import com.rg.service.entity.Type;

@Named
@SessionScoped
public class MoneyService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private MoneyDAO dao;

	@Inject
	private CommonDAO commonDao;

	public Money getMoneyById(Long moneyId) {
		return dao.getMoneyById(moneyId);
	}

	public Long saveMoney(Money money) {
		return dao.saveMoney(money);
	}

	public List<Money> getAllLoans(int personId) {
		List<Money> allLoans = new ArrayList<Money>();
		Type typeLoans = commonDao.getTypeByDescription(CommonConstants.TYPE_LOANS);
		List<Type> subTypes = typeLoans.getSubTypes();
		for (Type type : subTypes) {
			allLoans.addAll(dao.getMoneyListByType(type, personId));
		}
		return allLoans;
	}

	/* Below this only getter setter is allowed */

	public MoneyDAO getDao() {
		return dao;
	}

	public void setDao(MoneyDAO dao) {
		this.dao = dao;
	}

	public CommonDAO getCommonDao() {
		return commonDao;
	}

	public void setCommonDao(CommonDAO commonDao) {
		this.commonDao = commonDao;
	}
}