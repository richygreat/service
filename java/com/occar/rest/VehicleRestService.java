package com.occar.rest;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.inject.Inject;
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
import com.occar.entity.Person;
import com.occar.entity.Vehicle;
import com.occar.service.CommonService;
import com.occar.service.PersonService;
import com.occar.service.VehicleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Path("/vehicle")
@Api(value = "Vehicle")
public class VehicleRestService implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final Logger log = Logger.getLogger(VehicleRestService.class.getName());

	@Inject
	private CommonService commonService;

	@Inject
	private VehicleService vehicleService;

	@Inject
	private PersonService personService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Find a Vehicle by vehicleId")
	public VehicleBean getVehicleDetails(@QueryParam("q") String vehicleId) {
		VehicleBean bean = new VehicleBean();
		Vehicle vehicle = vehicleService.getVehicleById(Integer.valueOf(vehicleId));
		bean.setRegNumber(vehicle.getRegNumber());
		bean.setEngineNumber(vehicle.getEngineNumber());
		bean.setChassisNumber(vehicle.getChassisNumber());
		bean.setNoOfSeats(vehicle.getNoOfSeats());
		bean.setTypeId(vehicle.getType().getTypeId());
		return bean;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Create a new Vehicle")
	public void createVehicleDetails(VehicleBean bean) {
		log.info(bean.toString());
		Vehicle vehicle = new Vehicle();
		Person person = personService.getPersonById(bean.getPersonId());
		vehicle.setRegNumber(bean.getRegNumber());
		vehicle.setEngineNumber(bean.getEngineNumber());
		vehicle.setChassisNumber(bean.getChassisNumber());
		vehicle.setNoOfSeats(bean.getNoOfSeats());
		vehicle.setType(commonService.getTypeById(bean.getTypeId()));
		person.getVehicles().add(vehicle);
		personService.savePerson(person);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Update a Vehicle")
	public void updateVehicleDetails(VehicleBean bean) {
		log.info(bean.toString());
		Vehicle vehicle = vehicleService.getVehicleById(bean.getVehicleId());
		vehicle.setRegNumber(bean.getRegNumber());
		vehicle.setEngineNumber(bean.getEngineNumber());
		vehicle.setChassisNumber(bean.getChassisNumber());
		vehicle.setNoOfSeats(bean.getNoOfSeats());
		vehicle.setType(commonService.getTypeById(bean.getTypeId()));
		vehicleService.saveVehicle(vehicle);
	}

	@DELETE
	@Path("{personId}/{vehicleId}")
	@ApiOperation(value = "Delete a Vehicle by personId and vehicleId")
	public void deleteVehicleDetails(@PathParam("personId") String personId, @PathParam("vehicleId") String vehicleId) {
		log.info(vehicleId);
		vehicleService.deleteVehicle(Integer.valueOf(personId), Integer.valueOf(vehicleId));
	}

	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}

	public VehicleService getVehicleService() {
		return vehicleService;
	}

	public void setVehicleService(VehicleService vehicleService) {
		this.vehicleService = vehicleService;
	}

	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}
}