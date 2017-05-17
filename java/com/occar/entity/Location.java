package com.occar.entity;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * Entity implementation class for Entity: Location
 *
 */
@Entity
public class Location implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int locationId;
	private String latitude;
	private String longitude;
	private String name;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Company company;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Attribute> attributes = new LinkedList<Attribute>();
	private String googlePlaceId;
	private int routeOrder;

	private static final long serialVersionUID = 1L;

	public Location() {
		super();
	}

	@Override
	public String toString() {
		return "Location [locationId=" + locationId + ", latitude=" + latitude + ", longitude=" + longitude + ", name="
				+ name + ", company=" + company + ", attributes=" + attributes + ", googlePlaceId=" + googlePlaceId
				+ "]";
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public List<Attribute> getAttributes() {
		if (attributes == null) {
			attributes = new LinkedList<Attribute>();
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

	public String getGooglePlaceId() {
		return googlePlaceId;
	}

	public void setGooglePlaceId(String googlePlaceId) {
		this.googlePlaceId = googlePlaceId;
	}

	public int getRouteOrder() {
		return routeOrder;
	}

	public void setRouteOrder(int routeOrder) {
		this.routeOrder = routeOrder;
	}
}