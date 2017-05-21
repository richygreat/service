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

import com.rg.service.bean.rest.UserBean;
import com.rg.service.business.UserService;
import com.rg.service.constant.CommonConstants;
import com.rg.service.entity.User;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Path("/usr")
@Api(value = "Usr")
public class UserRestService implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final Logger log = Logger.getLogger(UserRestService.class.getName());

	@Inject
	private UserService service;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Find a User by personId")
	public UserBean getUser(@QueryParam("q") String personId) {
		UserBean bean = new UserBean();
		User user = service.getUserById(Integer.valueOf(personId));
		if (user != null) {
			log.info("Fetched User :: " + user);
			bean.setResult(CommonConstants.SUCCESS);
			bean.setPersonId(user.getPersonId());
			bean.setUserID(user.getUserID());
			bean.setAccessToken(user.getAccessToken());
			bean.setName(user.getName());
		} else {
			bean.setResult(CommonConstants.FAILURE);
		}
		return bean;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create a new User or Update Existing User Data from Facebook")
	public String saveUser(UserBean bean) {
		log.info(bean.toString());
		User user = null;
		user = service.getUserByUserID(bean.getUserID());
		if (user == null) {
			user = new User();
		}
		user.setAccessToken(bean.getAccessToken());
		user.setUserID(bean.getUserID());
		user.setName(bean.getName());
		
		int personId = service.saveUser(user);
		log.info("Created User PersonId :: " + personId);
		return String.valueOf(personId);
	}

	public UserService getService() {
		return service;
	}

	public void setService(UserService service) {
		this.service = service;
	}
}