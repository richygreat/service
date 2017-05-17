package com.occar.bean.admin;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.occar.entity.Location;
import com.occar.entity.Route;

@Named
@SessionScoped
public class RouteBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Route> routeList;
	private Route route;
	private int selectedRouteId;

	public List<Route> getRouteList() {
		return routeList;
	}

	public void setRouteList(List<Route> routeList) {
		this.routeList = routeList;
	}

	public Route getRoute() {
		if (route == null) {
			route = new Route();
			route.getStops().add(new Location());
		}
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public int getSelectedRouteId() {
		return selectedRouteId;
	}

	public void setSelectedRouteId(int selectedRouteId) {
		this.selectedRouteId = selectedRouteId;
	}
}
