package com.occar.rest.client;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;

import com.occar.bean.admin.AppConfig;
import com.occar.bean.rest.GooglePlacesAddBean;
import com.occar.bean.rest.GooglePlacesAddResponseBean;
import com.rg.service.constant.CommonConstants;

public class GoogleMapsRestClient {
	public static final String HTTP_SERVICE_URL = "https://maps.googleapis.com/maps/api/place/add/json?key="
			+ CommonConstants.GOOGLE_API_KEY;

	public static GooglePlacesAddResponseBean addStopToGoogle(double lat, double lng) {
		GooglePlacesAddResponseBean resp = null;
		ResteasyClient client = new ResteasyClientBuilder().build();
		ResteasyWebTarget target = client.target(HTTP_SERVICE_URL);
		PlacesAddRestClient rs = target.proxy(PlacesAddRestClient.class);
		GooglePlacesAddBean bean = new GooglePlacesAddBean(lat, lng);
		bean.setAccuracy(50);
		bean.setName(AppConfig.GOOGLE_CAR_STOP_NAME_ATTR_BEAN.getValue());
		bean.setAddress("Chennai");
		bean.getTypes()[0] = "taxi_stand";
		bean.setLanguage("en-US");
		resp = rs.createLocation(bean);
		return resp;
	}
}
