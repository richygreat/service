package com.occar.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.occar.bean.rest.GooglePlacesAddResponseBean;
import com.occar.dao.CommonDAO;
import com.occar.dao.DocumentDAO;
import com.occar.dao.RouteDAO;
import com.occar.dao.TripDAO;
import com.occar.entity.Document;
import com.occar.entity.Location;
import com.occar.entity.Menu;
import com.occar.entity.Route;
import com.occar.entity.Trip;
import com.occar.entity.Type;
import com.occar.rest.client.GoogleMapsRestClient;
import com.occar.util.common.CommonUtil;

@Named
@SessionScoped
public class CommonService implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(CommonService.class.getName());

	@Inject
	private CommonDAO dao;

	@Inject
	private DocumentDAO documentDao;

	@Inject
	private RouteDAO routeDao;
	
	@Inject
	private TripDAO tripDao;

	public List<Object> queryDB(String query) {
		return dao.queryDB(query);
	}

	/* Menu functions begin here */

	public List<Menu> getMenus() {
		return dao.getMenus();
	}

	public void saveMenu(Menu menu) {
		dao.saveMenu(menu);
	}

	public void deleteMenu(int menuId) {
		dao.deleteMenu(menuId);
	}

	public void saveMenuList(List<Menu> ls) {
		dao.saveMenuList(ls);
	}

	/* Menu functions end here */

	/* Type functions begin here */

	public Type getTypeByDescription(String typeDescription) {
		return dao.getTypeByDescription(typeDescription);
	}

	public Type getTypeById(int typeId) {
		return dao.getTypeById(typeId);
	}

	public List<Type> getTypes() {
		return dao.getTypes();
	}

	public void saveType(Type type) {
		dao.saveType(type);
	}

	public void deleteType(int typeId) {
		dao.deleteType(typeId);
	}

	public void saveTypeList(List<Type> ls) {
		dao.saveTypeList(ls);
	}

	public List<Type> getSubTypesByParentTypeDesc(String typeDescription) {
		return dao.getTypeByDescription(typeDescription).getSubTypes();
	}

	/* Type functions end here */

	/* Document functions begin here */

	public Document getDocumentById(int documentId) {
		return documentDao.getDocumentById(documentId);
	}

	public List<Document> getDocumentByDescription(String typeDescription) {
		return documentDao.getDocumentByDescription(typeDescription);
	}

	public int saveDocument(Document doc) {
		return documentDao.saveDocument(doc);
	}

	public Document deleteDocument(int docId, int personId) {
		return documentDao.deleteDocument(docId, personId);
	}

	public void saveDocument(List<Document> ls) {
		documentDao.saveDocumentList(ls);
	}

	/* Document functions end here */

	/* Route functions begin here */
	
	public Location getLocationById(int locationId) {
		return routeDao.getLocationById(locationId);
	}
	
	public Route getRouteById(int routeId) {
		return routeDao.getRouteById(routeId);
	}

	public List<Route> getRoutes() {
		return routeDao.getRoutes();
	}
	
	public List<Route> getRoutesForATrip(String stop, String destination) {
		return routeDao.getRoutesForATrip(stop, destination);
	}

	public void saveRoute(Route route) {
		List<String> addedStops = new ArrayList<String>();
		for (Iterator<Location> iterator = route.getStops().iterator(); iterator.hasNext();) {
			Location stop = iterator.next();
			if (!CommonUtil.isNullOrBlank(stop.getName())) {
				if (addedStops.contains(stop.getName())) {
					iterator.remove();
					continue;
				}
				addedStops.add(stop.getName());
			} else {
				iterator.remove();
			}
		}
		routeDao.saveRoute(route);

		for (Location stop : route.getStops()) {
			if (CommonUtil.isNullOrBlank(stop.getGooglePlaceId())) {
				GooglePlacesAddResponseBean googleResp = GoogleMapsRestClient
						.addStopToGoogle(Double.valueOf(stop.getLatitude()), Double.valueOf(stop.getLongitude()));
				if (googleResp != null) {
					if (CommonUtil.isNullOrBlank(googleResp.getPlace_id())) {
						log.info("Google DID NOT ADD the CAR STOP. Kindly Check. " + googleResp.toString());
					} else {
						stop.setGooglePlaceId(googleResp.getPlace_id());
					}
				} else {
					log.info("Google DID NOT ADD the CAR STOP. Kindly Check. " + stop.toString());
				}
			}
		}
		
		routeDao.saveRoute(route);
	}

	public void deleteRoute(int routeId) {
		routeDao.deleteRoute(routeId);
	}

	public void saveRouteList(List<Route> ls) {
		routeDao.saveRouteList(ls);
	}

	public Location getStopByName(String name) {
		return routeDao.getStopByName(name);
	}

	/* Route functions end here */
	
	/* Trip functions begin here */
	
	public int saveTrip(Trip trip) {
		return tripDao.saveTrip(trip);
	}
	
	public void saveTripWithPassenger(Trip trip, int passengerId) {
		tripDao.saveTripWithPassenger(trip, passengerId);
	}
	
	public Trip getTrip(int tripId){
		return tripDao.getTripById(tripId);
	}
	/* Trip functions end here */

	public CommonDAO getDao() {
		return dao;
	}

	public void setDao(CommonDAO dao) {
		this.dao = dao;
	}

	public DocumentDAO getDocumentDao() {
		return documentDao;
	}

	public void setDocumentDao(DocumentDAO documentDao) {
		this.documentDao = documentDao;
	}

	public RouteDAO getRouteDao() {
		return routeDao;
	}

	public void setRouteDao(RouteDAO routeDao) {
		this.routeDao = routeDao;
	}

	public TripDAO getTripDao() {
		return tripDao;
	}

	public void setTripDao(TripDAO tripDao) {
		this.tripDao = tripDao;
	}
}