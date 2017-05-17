package com.occar.dao;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.occar.entity.Location;
import com.occar.entity.Route;

@Named
@SessionScoped
public class RouteDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(RouteDAO.class.getName());

	@PersistenceContext(unitName = "occardb")
	private EntityManager entityManager;

	public RouteDAO() {
		log.info("RouteDAO Constructor called");
	}
	
	@Transactional
	public Location getLocationById(int locationId) {
		log.info("Entering getLocationById");
		Location location = entityManager.find(Location.class, locationId);
		log.info("Exiting getLocationById");
		return location;
	}
	
	@Transactional
	public Route getRouteById(int routeId) {
		log.info("Entering getRouteByDescription");
		Route route = entityManager.find(Route.class, routeId);
		log.info("Exiting getRouteByDescription");
		return route;
	}


	@Transactional
	public List<Route> getRoutes() {
		List<Route> routes = null;
		log.info("Entering getRoutes");
		routes = entityManager.createQuery("select r from Route r", Route.class).getResultList();
		log.info("Exiting getRoutes");
		return routes;
	}

	@Transactional
	public void deleteRoute(int routeId) {
		log.info("Entering deleteRoute");
		Route route = entityManager.find(Route.class, routeId);
		entityManager.remove(route);
		log.info("Entering deleteRoute");
	}

	@Transactional
	public void saveRoute(Route route) {
		log.info("Entering saveRoute");
		int i = 1;
		for (Iterator<Location> iterator = route.getStops().iterator(); iterator.hasNext();) {
			Location stop = iterator.next();
			if (stop.getName() != null && stop.getName().trim().length() != 0) {
				stop.setRouteOrder(i++);
				if (stop.getLocationId() == 0) {
					entityManager.persist(stop);
				} else {
					entityManager.merge(stop);
				}
			} else {
				iterator.remove();
			}
		}
		log.info("Persisting :: " + route);
		if (route.getRouteId() == 0) {
			entityManager.persist(route);
		} else {
			entityManager.merge(route);
		}
		log.info("Exiting saveRoute");
	}

	@Transactional
	public void saveRouteList(List<Route> ls) {
		log.info("Entering saveRouteList");
		for (Route route : ls) {
			saveRoute(route);
		}
		log.info("Exiting saveRouteList");
	}
	
	@Transactional
	public List<Route> getRoutesForATrip(String stop, String destination) {
		List<Route> routes = null;
		log.info("Entering getRoutesForATrip");
		routes = entityManager.createQuery("select r from Route r inner join r.stops st1 inner join r.stops st2 where st1.name=?1 and st2.name=?2", Route.class)
								.setParameter(1, destination)
								.setParameter(2, stop)
								.getResultList();
		log.info(routes.toString());
		log.info("Exiting getRoutesForATrip");
		return routes;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public Location getStopByName(String name) {
		log.info("Entering getStopByName");
		Location loc = null;
		try {
			loc = entityManager.createQuery("select l from Location l where l.name = ?1", Location.class)
					.setParameter(1, name).getSingleResult();
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		log.info("Exiting getStopByName");
		return loc;
	}
}
