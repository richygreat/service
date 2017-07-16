package com.rg.service.rest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Path("/loan")
@Api(value = "Loan")
public class LoanRestService implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final Logger log = Logger.getLogger(LoanRestService.class.getName());

	@Inject
	private MoneyService service;

	@Inject
	private UserService userService;

	@Inject
	private CommonService commonService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all loans")
	public List<MoneyBean> getLoans(@QueryParam("q") String personId) {
		List<MoneyBean> lsResult = null;
		MoneyBean bean = null;
		List<Money> allLoans = service.getAllLoans(Integer.valueOf(personId));
		if (allLoans != null && !allLoans.isEmpty()) {
			log.info("Fetched Loans :: " + allLoans);
			lsResult = new ArrayList<MoneyBean>();
			for (Money money : allLoans) {
				bean = new MoneyBean();
				bean.setResult(CommonConstants.SUCCESS);
				bean.setAmount(money.getAmount());
				bean.setEmi(money.getEmi());
				bean.setInstMonths(money.getInstMonths());
				bean.setInterestRate(money.getInterestRate());
				bean.setRemainInstMonths(money.getRemainInstMonths());
				bean.setDate(money.getAmortStartDate());
				bean.setDescription(money.getDescription());
				if (money.getType() != null) {
					bean.setType(money.getType().getDescription());
				}
				bean.setMoneyId(money.getMoneyId());
				bean.setUserID(money.getUser().getUserID());
				lsResult.add(bean);
			}
		}
		return lsResult;
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