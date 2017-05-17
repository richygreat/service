package com.occar.bean.rest;

import java.io.Serializable;

public class GooglePlacesAddResponseBean implements Serializable {
	private String id;
	private String place_id;
	private String reference;
	private String scope;
	private String status;

	private static final long serialVersionUID = 1L;

	public GooglePlacesAddResponseBean() {
		super();
	}

	@Override
	public String toString() {
		return "GooglePlacesAddResponseBean [id=" + id + ", place_id=" + place_id + ", reference=" + reference
				+ ", scope=" + scope + ", status=" + status + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlace_id() {
		return place_id;
	}

	public void setPlace_id(String place_id) {
		this.place_id = place_id;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}
}