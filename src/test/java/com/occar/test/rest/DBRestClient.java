package com.occar.test.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/db")
public interface DBRestClient {
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response query(@FormParam("q") String query, @FormParam("uid") String uid);
}