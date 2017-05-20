package com.occar.test.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.occar.bean.rest.DriverBean;

@Path("/driver")
public interface DriverServiceClient {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public DriverBean getDriverDetails(@QueryParam("q") String personId);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String saveDriverDetails(DriverBean bean);
}