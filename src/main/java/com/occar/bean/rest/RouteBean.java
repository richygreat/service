/**
 * 
 */
package com.occar.bean.rest;

import java.io.Serializable;
import java.util.List;

import com.google.maps.model.LatLng;

/**
 * @author Ramnath
 *
 */
public class RouteBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private String routeId;
	private String name;
	private List<LatLng> lsStops;

	public RouteBean() {
	}

	@Override
	public String toString() {
		return "RouteBean [routeId=" + routeId + ", name=" + name + ", lsStops=" + lsStops + "]";
	}

	public String getRouteId() {
		return routeId;
	}

	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<LatLng> getLsStops() {
		return lsStops;
	}

	public void setLsStops(List<LatLng> lsStops) {
		this.lsStops = lsStops;
	}

}
