package com.occar.rest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.maps.model.LatLng;
import com.occar.bean.rest.RouteBean;
import com.occar.entity.Location;
import com.occar.entity.Route;
import com.occar.service.CommonService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Rest service test sample
 * 
 * @author Richy
 *
 */
@Path("/pojo")
@Api(value = "Simple Rest API Test")
public class SimpleRESTPojo implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final Logger log = Logger.getLogger(SimpleRESTPojo.class.getName());

	@Inject
	private CommonService commonService;

	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Check if Rest services are working")
	public String pojo() {
		return "success";
	}

	@GET
	@Path("/route")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get Route Details")
	public RouteBean getRouteDetails(@QueryParam("q") String routeId) {
		RouteBean bean = new RouteBean();
		log.info("Entering getRouteDetails");
		Route route = commonService.getRouteById(Integer.valueOf(routeId));
		bean.setLsStops(new ArrayList<LatLng>());
		for (Location loc : route.getStops()) {
			LatLng l = new LatLng(Double.valueOf(loc.getLatitude()), Double.valueOf(loc.getLongitude()));
			bean.getLsStops().add(l);
		}
		log.info(bean.toString());
		log.info("Exiting getRouteDetails");
		return bean;
	}

	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}
}