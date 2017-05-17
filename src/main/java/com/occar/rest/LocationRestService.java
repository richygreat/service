package com.occar.rest;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.maps.model.PlacesSearchResult;
import com.occar.bean.rest.CoOrdinateBean;
import com.occar.service.CommonService;
import com.occar.util.common.GoogleUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author DhananJeyan
 * 
 *         Date of Creation : 08/07/2016 (DD/MM/YYYY) 
 *         <br>
 *         LocationFindService -
 *         Logic *
 *         <ul>
 *         <li>Finding nearest match of given Latitude and Longitude</li>
 *         <li>Return nearest of Latitude and Longitude
 *         </ul>
 *
 */
@Path("/location")
@Api(value = "Location")
public class LocationRestService implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final Logger log = Logger.getLogger(LocationRestService.class.getName());

	@Inject
	private CommonService service;

	public CoOrdinateBean getAddedLocation() {
		CoOrdinateBean response = new CoOrdinateBean();

		return response;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get the nearest Car Stop for a given location")
	public CoOrdinateBean findLocation(@FormParam("latitude") String latitude,
			@FormParam("longitude") String longitude) {
		CoOrdinateBean response = new CoOrdinateBean();
		log.info("latitude::" + latitude);
		log.info("longitude::" + longitude);
		try {
			PlacesSearchResult result = GoogleUtil.getNearestCarStop(Double.valueOf(latitude),
					Double.valueOf(longitude));
			if (result == null) {
				response.setResult("error-user-far-away");
			} else {
				response.setLatitude(String.valueOf(result.geometry.location.lat));
				response.setLongitude(String.valueOf(result.geometry.location.lng));
				response.setPlaceId(result.placeId);
				response.setResult("success");
			}
		} catch (NumberFormatException e) {
			log.log(Level.SEVERE, "Lat Lng conversion to double failed", e);
			response.setResult("error-input");
		} catch (Exception e) {
			log.log(Level.SEVERE, "Google Nearby search or DistanceMatrix failed", e);
			response.setResult("error-google");
		}
		log.info(response.toString());
		return response;
	}

	public CommonService getService() {
		return service;
	}

	public void setService(CommonService service) {
		this.service = service;
	}

}
