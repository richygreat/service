package com.occar.test.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/loan")
public interface LoanRestServiceClient {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getLoans(@QueryParam("q") String personId);
}