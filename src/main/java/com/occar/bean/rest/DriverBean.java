package com.occar.bean.rest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DriverBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private int personId;
	private String name;
	private String result;
	private List<String> vehicleRegNums;
	private String currentVehicle;
	private int currentVehicleTypeId;
	private int noOfSeats;

	@Override
	public String toString() {
		return "DriverBean [personId=" + personId + ", name=" + name + ", result=" + result + ", vehicleRegNums="
				+ vehicleRegNums + ", currentVehicle=" + currentVehicle + ", currentVehicleTypeId="
				+ currentVehicleTypeId + ", noOfSeats=" + noOfSeats + "]";
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

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public List<String> getVehicleRegNums() {
		if (vehicleRegNums == null) {
			vehicleRegNums = new ArrayList<String>();
		}
		return vehicleRegNums;
	}

	public void setVehicleRegNums(List<String> vehicleRegNums) {
		this.vehicleRegNums = vehicleRegNums;
	}

	public String getCurrentVehicle() {
		return currentVehicle;
	}

	public void setCurrentVehicle(String currentVehicle) {
		this.currentVehicle = currentVehicle;
	}

	public int getCurrentVehicleTypeId() {
		return currentVehicleTypeId;
	}

	public void setCurrentVehicleTypeId(int currentVehicleTypeId) {
		this.currentVehicleTypeId = currentVehicleTypeId;
	}

	public int getNoOfSeats() {
		return noOfSeats;
	}

	public void setNoOfSeats(int noOfSeats) {
		this.noOfSeats = noOfSeats;
	}
}
