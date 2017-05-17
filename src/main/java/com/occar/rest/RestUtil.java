package com.occar.rest;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import com.occar.bean.rest.RouteBean;
import com.occar.entity.Route;

public class RestUtil {
	public static final Logger log = Logger.getLogger(RestUtil.class.getName());

	public static void main(String[] args) {
	}

	public static List<RouteBean> getRestRouteBeans(List<Route> routes){
		List<RouteBean> restRouteBeans = new LinkedList<RouteBean>();
		for (Iterator<Route> iterator = routes.iterator(); iterator.hasNext();) {
			Route route = iterator.next();
			RouteBean routeBean = new RouteBean();
			routeBean.setRouteId(String.valueOf(route.getRouteId()));
			routeBean.setName(route.getName());
			restRouteBeans.add(routeBean);
		}
		return restRouteBeans;
	}
}
