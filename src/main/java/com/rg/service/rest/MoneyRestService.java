package com.rg.service.rest;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.rg.service.bean.rest.MoneyBean;
import com.rg.service.business.CommonService;
import com.rg.service.business.MoneyService;
import com.rg.service.business.UserService;
import com.rg.service.constant.CommonConstants;
import com.rg.service.entity.Money;
import com.rg.service.entity.Type;
import com.rg.service.entity.User;
import com.rg.service.util.common.CommonUtil;

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

	@Inject
	private CommonService commonService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Find a Money by moneyId")
	public MoneyBean getMoney(@QueryParam("q") String moneyId) {
		MoneyBean bean = new MoneyBean();
		Money money = service.getMoneyById(Long.valueOf(moneyId));
		if (money != null) {
			log.info("Fetched Money :: " + money);
			bean.setResult(CommonConstants.SUCCESS);
			bean.setAmount(money.getAmount());
			bean.setCredit(money.getCredit());
			bean.setDate(money.getDate());
			bean.setDescription(money.getDescription());
			if (money.getType() != null) {
				bean.setType(money.getType().getDescription());
			}
			bean.setMoneyId(money.getMoneyId());
			bean.setUserID(money.getUser().getUserID());
		} else {
			bean.setResult(CommonConstants.FAILURE);
		}
		return bean;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create a new Money entry or Update")
	public String saveMoney(MoneyBean bean) {
		log.info(bean.toString());
		Money money = new Money();
		money.setAmount(bean.getAmount());
		money.setCredit(bean.getCredit());
		money.setDate(bean.getDate());
		money.setDescription(bean.getDescription());
		if (!CommonUtil.isNullOrBlank(bean.getType())) {
			Type type = commonService.getTypeByDescription(bean.getType());
			money.setType(type);
		}
		User user = userService.getUserByUserID(bean.getUserID());
		if (user != null) {
			money.setUser(user);
		}
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

	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}
}