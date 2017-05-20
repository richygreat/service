/**
 * 
 */
package com.occar.test.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.occar.bean.rest.RouteBean;
import com.occar.bean.rest.TripBean;

/**
 * @author Ramnath
 * Proxy interface for PassengerService
 */
@Path("/trip")
public interface TripRestServiceClient {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<RouteBean> getRoutesForATrip(@QueryParam("stop") String stop, @QueryParam("dest")  String destination);
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public String startOrJoinTrip(TripBean bean);
}
