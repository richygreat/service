package com.occar.test.rest;

import java.util.Date;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.junit.Assert;
import org.junit.Test;

import com.rg.service.bean.rest.MoneyBean;
import com.rg.service.bean.rest.UserBean;

public class CommonRestTest {
	private static final String HTTP_SERVICE_URL = "http://localhost:8080/service/rest";

	@Test
	public void pojoGetServiceTest() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(HTTP_SERVICE_URL);
		SimpleRESTPojoClient simple = target.proxy(SimpleRESTPojoClient.class);
		Response response = simple.pojo();
		System.out.println(response.readEntity(String.class));
		Assert.assertNotNull(response);
	}

	@Test
	public void dbGetServiceTest() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(HTTP_SERVICE_URL);
		DBRestClient simple = target.proxy(DBRestClient.class);
		Response response = simple.query("select m from Menu m", "richy");
		System.out.println(response.readEntity(String.class));
		Assert.assertNotNull(response);
	}

	@Test
	public void userBasicTest() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(HTTP_SERVICE_URL);

		UserRestServiceClient userService = target.proxy(UserRestServiceClient.class);
		UserBean user = new UserBean();
		user.setName("Richy");
		user.setUserID("Richy");

		String personId = userService.saveUser(user);
		System.out.println("User Created :: " + personId);
		Assert.assertNotNull(personId);

		Response response = userService.getUser(personId);
		System.out.println(response.readEntity(String.class));
		Assert.assertNotNull(response);
	}

	@Test
	public void moneyBasicTest() {
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(HTTP_SERVICE_URL);

		MoneyRestServiceClient moneyService = target.proxy(MoneyRestServiceClient.class);
		MoneyBean money = new MoneyBean();
		money.setAmount(2000.00);
		money.setCredit(Boolean.TRUE);
		money.setDate(new Date());
		money.setUserID("Richy");

		String moneyId = moneyService.saveMoney(money);
		Assert.assertNotNull(moneyId);
	}
}
