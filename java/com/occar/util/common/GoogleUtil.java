package com.occar.util.common;

import java.util.logging.Logger;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.Distance;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import com.occar.bean.admin.AppConfig;
import com.occar.constant.CommonConstants;

/**
 * @author Richy
 *
 */
public class GoogleUtil {
	public static final Logger log = Logger.getLogger(GoogleUtil.class.getName());

	/**
	 * Pass the latitude and longitude to get the nearest Car Stop
	 * 
	 * @param lat
	 * @param lng
	 * @return PlacesSearchResult
	 * @throws Exception
	 */
	public static PlacesSearchResult getNearestCarStop(double lat, double lng) throws Exception {
		PlacesSearchResult result = null;
		GeoApiContext context = new GeoApiContext().setApiKey(CommonConstants.GOOGLE_API_KEY);
		LatLng latLng = new LatLng(lat, lng);
		/*
		 * First fetch the nearest places using nearby search. This might return
		 * more than one row.
		 */
		PlacesSearchResponse resp = PlacesApi.nearbySearchQuery(context, latLng)
				.name(AppConfig.GOOGLE_CAR_STOP_NAME_ATTR_BEAN.getValue()).radius(500).await();

		/*
		 * Now pass these places to DistanceMatrixApi and get the travel
		 * distance between the user location and get the shortest and best
		 * match
		 */
		long leastDistance = Long.MAX_VALUE;
		for (PlacesSearchResult res : resp.results) {
			Distance distance = DistanceMatrixApi.newRequest(context).origins(latLng)
					.destinations(res.geometry.location).await().rows[0].elements[0].distance;
			log.info("LatLng :: " + res.geometry.location + " Distance :: " + distance.inMeters);
			if (leastDistance > distance.inMeters) {
				result = res;
			}
		}
		return result;
	}
}
