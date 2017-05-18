package com.occar.rest;

import java.io.Serializable;
import java.util.Date;
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

import com.occar.bean.rest.RouteBean;
import com.occar.bean.rest.TripBean;
import com.occar.entity.Location;
import com.occar.entity.Person;
import com.occar.entity.Route;
import com.occar.entity.Trip;
import com.occar.entity.Vehicle;
import com.occar.service.PersonService;
import com.rg.service.business.CommonService;
import com.rg.service.constant.CommonConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Path("/trip")
@Api(value = "Trip")
public class TripRestService implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final Logger log = Logger.getLogger(TripRestService.class.getName());

	@Inject
	private PersonService personService;

	@Inject
	private CommonService commonService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Find Routes for a given origin and destination")
	public List<RouteBean> getRoutesForATrip(@QueryParam("stop") String stop, @QueryParam("dest") String destination) {
		log.info("Entering getRoutesForATrip Service");
		log.info("Destination = " + destination.toString());
		Location entityStop = new Location();
		entityStop.setName(stop);
		Location entityDest = new Location();
		entityDest.setName(destination);
		List<Route> routes = commonService.getRoutesForATrip(stop, destination);
		log.info("Exit getRoutesForATrip Service");
		return RestUtil.getRestRouteBeans(routes);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Start a new trip or join an existing trip")
	public String startOrJoinTrip(TripBean bean) {
		log.info("Entering startOrJoinTrip");
		log.info(bean.toString());
		String message = null;
		if(bean.getTripId() == 0){
			message = startTrip(bean);
		}else{
			message = joinTrip(bean)? "Passenger linked to trip successfully" : "No seats available";
		}
		log.info("Exiting startOrJoinTrip");
		return message;
	}

	private String startTrip(TripBean bean) {
		Trip trip = new Trip();
		trip.setDescription(bean.getDescription());
		Location origin = commonService.getLocationById(bean.getOriginLocId());
		Location destination = commonService.getLocationById(bean.getDestLocId());
		Date now = new Date();
		log.info("origin :: " + origin);
		log.info("destination :: " + destination);
		log.info("tripDate :: " + now);
		trip.setOrigin(origin);
		trip.setDestination(destination);
		trip.setTripDate(now);
		if (CommonConstants.TRIP_OPERATION_DRIVER_START.equals(bean.getOperation())) {
			Person driver = personService.getPersonById(bean.getDriverPersonId());
			log.info("driver found ::" + driver.toString());
			List<Vehicle> vehicles = driver.getVehicles();
			for (Vehicle vehicle : vehicles) {
				if(vehicle.getRegNumber().equals(driver.getAttributeByKey(CommonConstants.DEFAULT_VEHICLE_ATTR).getValue())){
					trip.setSeatsAvailable(vehicle.getNoOfSeats());
				}
			}
			Route route = commonService.getRouteById(bean.getRouteId());
			if(route != null) {
				log.info("route found ::" + route.toString());
				trip.setRoute(route);
			}
			trip.setDriver(driver);
		}
		log.info(trip.toString());
		int tripId = commonService.saveTrip(trip);
		log.info("Created new Trip :: " + tripId);
		return String.valueOf(tripId);
	}
	
	/**
	 * returns false if there are no seats available and true if passenger is linked with the trip
	 * @param bean
	 * @return
	 */
	private boolean joinTrip(TripBean bean){
		Trip trip = commonService.getTrip(Integer.valueOf(bean.getTripId()));
		int remainingSeats = trip.getSeatsAvailable() - 1;
		if(remainingSeats < 0){
			return false; // cannot proceed with the trip
		}else{
			trip.setSeatsAvailable(remainingSeats);
			commonService.saveTripWithPassenger(trip, bean.getPassengerId());
			return true;
		}
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
