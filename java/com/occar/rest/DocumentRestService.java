package com.occar.rest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
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

import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import com.occar.bean.rest.UploadDocumentBean;
import com.occar.bean.rest.UserBean;
import com.occar.entity.Document;
import com.occar.entity.Person;
import com.occar.entity.Type;
import com.occar.service.CommonIOService;
import com.occar.service.CommonService;
import com.occar.service.PersonService;
import com.occar.util.common.CommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Rest service for user document upload/download/delete
 * 
 * @author Ramnath
 *
 */
@Path("/file")
@Api(value = "Document")
public class DocumentRestService implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final Logger log = Logger.getLogger(DocumentRestService.class.getName());
	private static final String FILE_PREFIX = "OC_";
	private static final String DIRECTORY_PATH = "/var/lib/openshift/576c156489f5cfda2f000103/app-root/data/files/";

	private static final Map<String, String> CONTENT_TYPE = new HashMap<String, String>();

	static {
		CONTENT_TYPE.put("image/jpeg", "jpg");
		CONTENT_TYPE.put("image/png", "png");
		CONTENT_TYPE.put("image/bmp", "bmp");
	}

	@Inject
	private CommonService service;
	@Inject
	private CommonIOService ioService;
	@Inject
	private PersonService personService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get Document details for a given documentId")
	public UploadDocumentBean getDocumentDetails(@QueryParam("q") String docId) {
		log.info("Entering getDocumentDetails");
		UploadDocumentBean bean = new UploadDocumentBean();
		Document entityDoc = service.getDocumentById(Integer.valueOf(docId));
		bean = getRestDocFromEntityDoc(entityDoc);
		log.info("Exiting getDocumentDetails");
		return bean;
	}

	/**
	 * Creates a new entry in Document table as well as write the file to
	 * directory
	 * 
	 * @param doc
	 * @return
	 */
	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@ApiOperation(value = "Upload a new Document")
	public String uploadFile(@MultipartForm UploadDocumentBean doc) {
		log.info("Entering upload service");
		log.info("Get person");
		Person person = personService.getPersonById(Integer.valueOf(doc.getPersonId()));

		/* Identify content type and validate */
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(doc.getContent());
			String contentType = URLConnection.guessContentTypeFromStream(bis);
			log.info("contentType :: " + contentType);
			if (!CONTENT_TYPE.containsKey(contentType)) {
				throw new RuntimeException("Content Type Not Supported");
			}
			doc.setExtension(CONTENT_TYPE.get(contentType));
			bis.close();
		} catch (IOException e) {
			log.log(Level.SEVERE, "Content determination failed", e);
		}

		Document entityDoc = new Document();
		if (!CommonUtil.isNullOrBlank(doc.getType())) {
			Type type = service.getTypeByDescription(doc.getType());
			log.info("Type Obtained :: " + type.toString());
			if(type != null) {
				entityDoc.setType(type);
			}
		}
		
		log.info("Save document");
		doc.setDocumentId(String.valueOf(service.saveDocument(entityDoc)));
		log.info("Write file to directory");
		entityDoc.setFileName(generateFileName(doc, person.getName()));
		ioService.writeToFile(doc.getContent(), entityDoc.getFileName());
		log.info("Update Person with document and file name");
		person.getDocuments().add(entityDoc);
		personService.savePerson(person);
		log.info("Exiting upload service");
		return "create successful";
	}

	/**
	 * Deletes a file with given document id
	 * 
	 * @param docId
	 * @return
	 */
	@DELETE
	@Path("{docId}/{personId}")
	@ApiOperation(value = "Delete Document by documentId and personId")
	public String deleteFile(@PathParam("docId") String docId, @PathParam("personId") String personId) {
		log.info("Entering delete service");
		int documentId = 0;
		int personIdInt = 0;
		try {
			documentId = Integer.parseInt(docId);
			personIdInt = Integer.parseInt(personId);
		} catch (NumberFormatException e) {
			log.severe("Invalid document or person id");
			return "Invalid document id";
		}
		log.info("delete document from db");
		UploadDocumentBean doc = getRestDocFromEntityDoc(service.deleteDocument(documentId, personIdInt));
		log.info("delete document from directory");
		ioService.deleteFile(doc.getFileName());
		log.info("Exiting delete service");
		return "delete successful";

	}

	@PUT
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@ApiOperation(value = "Update Document with new data")
	public void updateFile(@MultipartForm UploadDocumentBean doc) {
		log.info("Entering update service");
		Document entityDoc = service.getDocumentById(Integer.valueOf(doc.getDocumentId()));
		doc.setFileName(entityDoc.getFileName());
		ioService.writeToFile(doc.getContent(), doc.getFileName());
		log.info("Exit update service");
	}

	public CommonService getService() {
		return service;
	}

	public void setService(CommonService service) {
		this.service = service;
	}

	public CommonIOService getIoService() {
		return ioService;
	}

	public void setIoService(CommonIOService ioService) {
		this.ioService = ioService;
	}

	private static UploadDocumentBean getRestDocFromEntityDoc(final Document entityDoc) {
		UploadDocumentBean doc = new UploadDocumentBean();
		doc.setDocumentId(String.valueOf(entityDoc.getDocumentId()));
		doc.setFileName(entityDoc.getFileName());
		UserBean person = new UserBean();
		person.setPersonId(entityDoc.getPerson().getPersonId());
		person.setName(entityDoc.getPerson().getName());
		doc.setPerson(person);
		return doc;
	}

	private String generateFileName(final UploadDocumentBean doc, String personName) {
		return CommonUtil.getConcatenatedString(DIRECTORY_PATH, FILE_PREFIX, personName, doc.getDocumentId(), ".",
				doc.getExtension());
	}

}
