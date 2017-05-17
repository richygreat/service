/**
 * 
 */
package com.occar.bean.rest;

import java.io.Serializable;
import java.util.List;

/**
 * @author Richy
 *
 */
public class TripBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private int tripId;
	private int routeId;
	private String description;
	private List<String> passengers;
	private int driverPersonId;
	private int passengerId;
	private String operation;
	private int originLocId;
	private int destLocId;

	public TripBean() {
	}

	@Override
	public String toString() {
		return "TripBean [tripId=" + tripId + ", routeId=" + routeId + ", description=" + description + ", passengers="
				+ passengers + ", driverPersonId=" + driverPersonId + ", operation=" + operation + ", originLocId="
				+ originLocId + ", destLocId=" + destLocId + "]";
	}

	public int getTripId() {
		return tripId;
	}

	public void setTripId(int tripId) {
		this.tripId = tripId;
	}

	public int getRouteId() {
		return routeId;
	}

	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<String> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<String> passengers) {
		this.passengers = passengers;
	}

	public int getDriverPersonId() {
		return driverPersonId;
	}

	public void setDriverPersonId(int driverPersonId) {
		this.driverPersonId = driverPersonId;
	}

	public int getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(int passengerId) {
		this.passengerId = passengerId;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public int getOriginLocId() {
		return originLocId;
	}

	public void setOriginLocId(int originLocId) {
		this.originLocId = originLocId;
	}

	public int getDestLocId() {
		return destLocId;
	}

	public void setDestLocId(int destLocId) {
		this.destLocId = destLocId;
	}
}
