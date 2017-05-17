package com.occar.rest.client;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.MediaType;

import com.occar.bean.rest.GooglePlacesAddBean;
import com.occar.bean.rest.GooglePlacesAddResponseBean;

public interface PlacesAddRestClient {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public GooglePlacesAddResponseBean createLocation(GooglePlacesAddBean bean);
}
