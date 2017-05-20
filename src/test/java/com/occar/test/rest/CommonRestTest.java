package com.occar.test.rest;

import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.junit.Assert;
import org.junit.Test;

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
		UserBean passenger = new UserBean();
		passenger.setName("Richy");
		String passengerId = userService.saveUser(passenger);
		System.out.println("User Created :: " + passengerId);
		Assert.assertNotNull(passengerId);
		
		Response response = userService.getUser(passengerId);
		System.out.println(response.readEntity(String.class));
		Assert.assertNotNull(response);
	}

}
