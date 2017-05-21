package com.occar.test.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.rg.service.bean.rest.MoneyBean;

@Path("/money")
public interface MoneyRestServiceClient {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getMoney(@QueryParam("q") String moneyId);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String saveMoney(MoneyBean bean);
}