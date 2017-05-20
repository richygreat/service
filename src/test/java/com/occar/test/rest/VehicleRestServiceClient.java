package com.occar.test.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.occar.bean.rest.VehicleBean;

@Path("/vehicle")
public interface VehicleRestServiceClient {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public VehicleBean getVehicleDetails(@QueryParam("q") String vehicleId);

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void createVehicleDetails(VehicleBean bean);

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateVehicleDetails(VehicleBean bean);

	@DELETE
	@Path("{personId}/{vehicleId}")
	public void deleteVehicleDetails(@PathParam("personId") String personId, @PathParam("vehicleId") String vehicleId);
}