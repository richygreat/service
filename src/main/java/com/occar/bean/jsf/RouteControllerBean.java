package com.occar.bean.jsf;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.occar.bean.admin.RouteBean;
import com.occar.entity.Location;
import com.occar.entity.Route;
import com.rg.service.business.CommonService;
import com.rg.service.util.filter.FilterUtil;
import com.rg.service.util.filter.IPredicate;

@Named("routeController")
@SessionScoped
public class RouteControllerBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(RouteControllerBean.class.getName());

	@Inject
	private CommonService service;

	@Inject
	private RouteBean routeBean;

	public String addMoreStops() {
		log.info("Entering addMoreStops");
		Location stop = new Location();
		List<Location> stops = routeBean.getRoute().getStops();
		if (stops.isEmpty()) {
			stop.setRouteOrder(1);
		} else {
			Location lastLocation = stops.get(stops.size() - 1);
			stop.setRouteOrder(lastLocation.getRouteOrder() + 1);
		}
		stops.add(stop);
		log.info("Exiting addMoreStops");
		return "success";
	}

	public String deleteStop(String index) {
		log.info("Entering deleteStop");
		log.info("Going to delete index :: " + index);
		routeBean.getRoute().getStops().remove(Integer.parseInt(index));
		log.info("Exiting deleteStop");
		return "success";
	}
	
	public String searchStop(String index) {
		log.info("Entering searchStop");
		log.info("Going to search data for index :: " + index);
		Location stop = routeBean.getRoute().getStops().get(Integer.parseInt(index));
		log.info(stop.toString());
		Location searchedStop = service.getStopByName(stop.getName());
		if(searchedStop != null) {
			log.info(searchedStop.toString());
			routeBean.getRoute().getStops().set(Integer.parseInt(index), searchedStop);
		}else {
			log.info("Searched Stop Name not found");
		}
		log.info("Exiting searchStop");
		return "success";
	}

	public String addRoute() {
		log.info("Entering addRoute");
		boolean newlyAdded = false;
		if (routeBean.getRoute().getRouteId() == 0) {
			newlyAdded = true;
		}
		service.saveRoute(routeBean.getRoute());
		if (newlyAdded) {
			routeBean.getRouteList().add(routeBean.getRoute());
		}
		routeBean.setSelectedRouteId(0);
		log.info("Exiting addRoute");
		return "success";
	}

	public String editRoute(String routeId) {
		log.info("Entering editRoute");
		routeBean.setSelectedRouteId(Integer.parseInt(routeId));
		log.info("Selected Route :: " + routeBean.getSelectedRouteId());
		IPredicate<Route> routeFilter = new IPredicate<Route>() {
			@Override
			public boolean apply(Route route) {
				return route.getRouteId() == Integer.parseInt(routeId);
			}
		};
		Route route = FilterUtil.filter(routeBean.getRouteList(), routeFilter).get(0);
		routeBean.setRoute(route);
		log.info("Selected Route Bean :: " + routeBean.getRoute());
		log.info("Exiting editRoute");
		return "success";
	}

	public String deleteRoute(String routeId) {
		log.info("Entering deleteRoute");
		log.info("Selected Route :: " + routeId);
		service.deleteRoute(Integer.parseInt(routeId));
		IPredicate<Route> routeFilter = new IPredicate<Route>() {
			@Override
			public boolean apply(Route route) {
				return route.getRouteId() == Integer.parseInt(routeId);
			}
		};
		Route route = FilterUtil.filter(routeBean.getRouteList(), routeFilter).get(0);
		routeBean.getRouteList().remove(route);
		log.info(routeBean.getRouteList().toString());
		if (routeBean.getRouteList().isEmpty()) {
			routeBean.setSelectedRouteId(-1);
			routeBean.setRoute(null);
		}
		log.info("Exiting deleteRoute");
		return "success";
	}

	public String addRoutePrepare() {
		log.info("Entering addRoutePrepare");
		routeBean.setSelectedRouteId(-1);
		routeBean.setRoute(null);
		log.info("Exiting addRoutePrepare");
		return "success";
	}

	public String cancelEdit() {
		log.info("Entering cancelEdit");
		routeBean.setSelectedRouteId(0);
		log.info("Exiting cancelEdit");
		return "success";
	}

	public CommonService getService() {
		return service;
	}

	public void setService(CommonService service) {
		this.service = service;
	}

	public RouteBean getRouteBean() {
		return routeBean;
	}

	public void setRouteBean(RouteBean routeBean) {
		this.routeBean = routeBean;
	}
}
