/**
 * 
 */
package com.occar.test.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import com.occar.bean.rest.UploadDocumentBean;

/**
 * @author Ramnath
 * proxy interface for testing document upload
 *
 */

@Path("/file")
public interface DocumentRestClient {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public UploadDocumentBean getDocumentDetails(@QueryParam("q") String docId);
	
	@POST
	@Consumes("multipart/form-data")
	public Response uploadFile(@MultipartForm UploadDocumentBean doc);
	
	@DELETE
	@Path("{docId}/{personId}")
	public Response deleteFile(@PathParam("docId") String docId, @PathParam("personId") String personId);
	
	@PUT
	@Consumes("multipart/form-data")
	public Response updateFile(@MultipartForm UploadDocumentBean doc);
}
