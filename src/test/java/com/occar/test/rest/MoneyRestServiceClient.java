package com.occar.test.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

import com.rg.service.bean.rest.MoneyBean;

@Path("/money")
public interface MoneyRestServiceClient {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String saveMoney(MoneyBean bean);
}