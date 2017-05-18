package com.rg.service.rest;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.occar.bean.rest.DBDataBean;
import com.rg.service.business.CommonService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Rest service for simple DB operations
 * 
 * @author Richy
 *
 */
@Path("/db")
@Api(value = "DB")
public class DBRestService implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final Logger log = Logger.getLogger(DBRestService.class.getName());

	@Inject
	private CommonService service;

	/**
	 * Query our DB
	 * 
	 * @param query
	 * @param uid
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Query DB")
	public DBDataBean query(@FormParam("q") String query, @FormParam("uid") String uid) {
		DBDataBean response = new DBDataBean();
		log.info("query::" + query);
		log.info("uid::" + uid);
		service.queryDB(query);
		log.info(response.toString());
		return response;
	}

	public CommonService getService() {
		return service;
	}

	public void setService(CommonService service) {
		this.service = service;
	}
}