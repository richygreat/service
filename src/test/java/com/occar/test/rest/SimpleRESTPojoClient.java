package com.occar.test.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/pojo")
public interface SimpleRESTPojoClient {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response pojo();
}