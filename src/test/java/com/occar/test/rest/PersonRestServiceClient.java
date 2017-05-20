package com.occar.test.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rg.service.bean.rest.UserBean;

@Path("/user")
public interface PersonRestServiceClient {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getUser(@QueryParam("q") String personId);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String saveUser(UserBean bean);
}