package com.occar.rest;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.occar.bean.rest.DriverBean;
import com.occar.entity.Person;
import com.occar.entity.Vehicle;
import com.occar.service.PersonService;
import com.rg.service.business.CommonService;
import com.rg.service.constant.CommonConstants;
import com.rg.service.entity.Attribute;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Path("/driver")
@Api(value = "Driver")
public class DriverRestService implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final String DEFAULT_VEHICLE_ATTR = "DEFAULT_VEHICLE";

	public static final Logger log = Logger.getLogger(DriverRestService.class.getName());

	@Inject
	private PersonService personService;

	@Inject
	private CommonService commonService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get Driver details like Vehicle data for a given personId")
	public DriverBean getDriverDetails(@QueryParam("q") String personId) {
		DriverBean bean = new DriverBean();
		Person person = personService.getPersonById(Integer.valueOf(personId));
		if (person != null) {
			log.info(person.toString());
			bean.setResult(CommonConstants.SUCCESS);
			bean.setPersonId(person.getPersonId());
			bean.setName(person.getName());
			List<Vehicle> lsVehicles = person.getVehicles();
			for (Vehicle vehicle : lsVehicles) {
				bean.getVehicleRegNums().add(vehicle.getRegNumber());
			}
			bean.setCurrentVehicle(person.getAttributeByKey(DEFAULT_VEHICLE_ATTR).getValue());
		} else {
			bean.setResult(CommonConstants.FAILURE);
		}
		return bean;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Save Driver details like Vehicle data")
	public String saveDriverDetails(DriverBean bean) {
		log.info(bean.toString());
		Person person = personService.getPersonById(bean.getPersonId());
		if (person == null) {
			person = new Person();
			person.setName(bean.getName());
			Vehicle vehicle = new Vehicle();
			vehicle.setRegNumber(bean.getCurrentVehicle());
			vehicle.setType(commonService.getTypeById(bean.getCurrentVehicleTypeId()));
			vehicle.setNoOfSeats(bean.getNoOfSeats());
			Attribute defaultVehicle = new Attribute(DEFAULT_VEHICLE_ATTR, bean.getCurrentVehicle());
			person.getAttributes().add(defaultVehicle);
			person.getVehicles().add(vehicle);
		}
		log.info(person.toString());
		int personId = personService.savePerson(person);
		log.info("Created Driver PersonId :: " + personId);
		return String.valueOf(personId);
	}

	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	public CommonService getCommonService() {
		return commonService;
	}

	public void setCommonService(CommonService commonService) {
		this.commonService = commonService;
	}
}