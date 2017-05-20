package com.occar.rest;

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

import com.occar.entity.Person;
import com.occar.service.PersonService;
import com.rg.service.bean.rest.UserBean;
import com.rg.service.constant.CommonConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Path("/user")
@Api(value = "User")
public class PersonRestService implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final Logger log = Logger.getLogger(PersonRestService.class.getName());

	@Inject
	private PersonService service;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Find a User by personId")
	public UserBean getUser(@QueryParam("q") String personId) {
		UserBean bean = new UserBean();
		Person person = service.getPersonById(Integer.valueOf(personId));
		if (person != null) {
			bean.setResult(CommonConstants.SUCCESS);
			bean.setPersonId(person.getPersonId());
			bean.setUserID(person.getUserID());
			bean.setAccessToken(person.getAccessToken());
			bean.setName(person.getName());
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
		Person person = null;
		person = service.getPersonByUserID(bean.getUserID());
		if (person == null) {
			person = new Person();
		}
		person.setAccessToken(bean.getAccessToken());
		person.setUserID(bean.getUserID());
		person.setName(bean.getName());
		int personId = service.savePerson(person);
		log.info("Created User PersonId :: " + personId);
		return String.valueOf(personId);
	}

	public PersonService getService() {
		return service;
	}

	public void setService(PersonService service) {
		this.service = service;
	}
}