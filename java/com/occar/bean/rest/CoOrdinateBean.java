package com.occar.bean.rest;

import java.io.Serializable;

/**
 * 
 * @author DhananJeyan
 * 
 *         Date of creation : 08/07/2016 (DD/MM/YYYY).
 * 
 *         <ul>
 *         CoOrdinateBean has Latitude and Longitude
 * 
 *         <li>Used to Get and Set values</li>
 * 
 *         </ul>
 * 
 *
 */
public class CoOrdinateBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String latitude;
	private String longitude;
	private String placeId;
	private String result;

	@Override
	public String toString() {
		return "CoOrdinateBean [latitude=" + latitude + ", longitude=" + longitude + "]";
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

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

}
