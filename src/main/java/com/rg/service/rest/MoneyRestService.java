package com.rg.service.rest;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.rg.service.bean.rest.MoneyBean;
import com.rg.service.business.MoneyService;
import com.rg.service.business.UserService;
import com.rg.service.entity.Money;
import com.rg.service.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Path("/money")
@Api(value = "Money")
public class MoneyRestService implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final Logger log = Logger.getLogger(MoneyRestService.class.getName());

	@Inject
	private MoneyService service;

	@Inject
	private UserService userService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create a new Money entry or Update")
	public String saveUser(MoneyBean bean) {
		log.info(bean.toString());
		User user = null;
		user = userService.getUserByUserID(bean.getUserID());
		if (user == null) {
			user = new User();
			user.setUserID(bean.getUserID());
		}

		Money money = new Money();
		money.setAmount(bean.getAmount());
		money.setDate(bean.getDate());
		money.setUser(user);

		Long moneyId = service.saveMoney(money);
		log.info("Created Money moneyId :: " + moneyId);
		return String.valueOf(moneyId);
	}

	public MoneyService getService() {
		return service;
	}

	public void setService(MoneyService service) {
		this.service = service;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}