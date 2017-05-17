package com.occar.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 * Entity implementation class for Entity: Person
 *
 */
@Entity
public class Person implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int personId;
	private String name;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Vehicle> vehicles = new LinkedList<Vehicle>();
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Document> documents = new LinkedList<Document>();
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Attribute> attributes;
	private String userID;
	private String accessToken;
	private String rejectionReason;

	private static final long serialVersionUID = 1L;

	public Person() {
		super();
	}

	@Override
	public String toString() {
		return "Person [personId=" + personId + ", name=" + name + ", vehicles=" + vehicles + ", documents=" + documents
				+ ", attributes=" + attributes + "]";
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	public List<Document> getDocuments() {
		return documents;
	}

	public void setDocuments(List<Document> documents) {
		this.documents = documents;
	}

	public List<Attribute> getAttributes() {
		if (attributes == null) {
			attributes = new ArrayList<Attribute>();
		}
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}

	public Attribute getAttributeByKey(String key) {
		for (Attribute attribute : attributes) {
			if (key.equals(attribute.getKey())) {
				return attribute;
			}
		}
		return null;
	}
	
	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}
}