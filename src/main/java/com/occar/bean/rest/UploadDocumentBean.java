package com.occar.bean.rest;

import java.io.Serializable;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import com.rg.service.bean.rest.UserBean;

public class UploadDocumentBean implements Serializable{

	private static final long serialVersionUID = 1L;
	private byte[] content;
	private int vehicleId;
	private String documentId;
	private String fileName;
	private String personId;
	private String extension;
	private String type;
	private UserBean person;
	
	public UploadDocumentBean() {

	}

	public String getType() {
		return type;
	}
	@FormParam("docType")
	public void setType(String type) {
		this.type = type;
	}

	public String getExtension() {
		return extension;
	}
	@FormParam("docExtension")
	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getDocumentId() {
		return documentId;
	}

	@FormParam("docId")
	public void setDocumentId(String docId) {
		this.documentId = docId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getPersonId() {
		return personId;
	}

	@FormParam("personId")
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	
	public UserBean getPerson() {
		return person;
	}

	public void setPerson(UserBean person) {
		this.person = person;
	}

	public byte[] getContent() {
		return content;
	}

	@FormParam("uploadedDoc")
	@PartType(MediaType.APPLICATION_OCTET_STREAM)
	public void setContent(byte[] content) {
		this.content = content;
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

}
