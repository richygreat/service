package com.rg.service.dao;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.rg.service.entity.Money;
import com.rg.service.entity.Type;

@Named
@SessionScoped
public class MoneyDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(MoneyDAO.class.getName());

	@PersistenceContext(unitName = "servicedb")
	private EntityManager entityManager;

	public MoneyDAO() {
		log.info("MoneyDAO Constructor called");
	}

	@Transactional
	public List<Money> getMoneyListByType(Type type, int personId) {
		log.info("Entering getMoneyListByType");
		List<Money> lsMoney = entityManager
				.createQuery("select m from Money m where m.type.typeId = " + type.getTypeId() + " and m.user.personId = " + personId, Money.class)
				.getResultList();
		log.info("Exiting getMoneyListByType");
		return lsMoney;
	}

	@Transactional
	public Money getMoneyById(Long moneyId) {
		log.info("Entering getMoneyById");
		Money money = entityManager.find(Money.class, moneyId);
		log.info("Exiting getMoneyById");
		return money;
	}

	@Transactional
	public Long saveMoney(Money money) {
		log.info("Entering saveMoney");
		log.info("Persisting :: " + money);
		if (money.getMoneyId() == null) {
			entityManager.persist(money);
		} else {
			entityManager.merge(money);
		}
		log.info("Exiting saveMoney");
		log.info("money id" + money.getMoneyId());
		return money.getMoneyId();
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
