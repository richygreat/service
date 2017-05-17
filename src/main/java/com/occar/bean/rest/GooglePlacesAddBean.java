package com.occar.bean.rest;

import java.io.Serializable;
import java.util.Arrays;

public class GooglePlacesAddBean implements Serializable {
	private GoogleLocationBean location;
	private int accuracy;
	private String name;
	private String phone_number;
	private String address;
	private String[] types = new String[1];
	private String website;
	private String language;

	private static final long serialVersionUID = 1L;
	
	public GooglePlacesAddBean(double lat, double lng) {
		GoogleLocationBean l = new GoogleLocationBean();
		l.setLat(lat);
		l.setLng(lng);
		this.setLocation(l);
	}

	public GooglePlacesAddBean() {
		super();
	}

	@Override
	public String toString() {
		return "PlacesAddBean [location=" + location + ", accuracy=" + accuracy + ", name=" + name + ", phone_number="
				+ phone_number + ", address=" + address + ", types=" + Arrays.toString(types) + ", website=" + website
				+ ", language=" + language + "]";
	}

	public GoogleLocationBean getLocation() {
		return location;
	}

	public void setLocation(GoogleLocationBean location) {
		this.location = location;
	}

	public int getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String[] getTypes() {
		return types;
	}

	public void setTypes(String[] types) {
		this.types = types;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
}

class GoogleLocationBean {
	private double lat;
	private double lng;

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

}