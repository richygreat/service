package com.occar.test.rest;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
/**
 * 
 * @author DhananJeyan
 * Date of Creation : 08/07/2016
 * <ul> LocationRestClient It is used as kind of Marker Interface
 * <li>Result of WebService Map<li>
 * <ul>
 * 
 */
@Path("/location")
public interface LocationRestClient {
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Response findLocation(@FormParam("latitude") String latitude, @FormParam("longitude") String longitude);
}
