package com.occar.bean.rest;

import java.io.Serializable;

public class VehicleBean implements Serializable {
	private int vehicleId;
	private String regNumber;
	private String engineNumber;
	private String chassisNumber;
	private String fuelType;
	private Double mileage;
	private int noOfSeats;
	private int rank;
	private int typeId;
	private String result;
	private int personId;

	private static final long serialVersionUID = 1L;

	public VehicleBean() {
		super();
	}

	@Override
	public String toString() {
		return "VehicleBean [vehicleId=" + vehicleId + ", regNumber=" + regNumber + ", engineNumber=" + engineNumber
				+ ", chassisNumber=" + chassisNumber + ", fuelType=" + fuelType + ", mileage=" + mileage
				+ ", noOfSeats=" + noOfSeats + ", rank=" + rank + ", typeId=" + typeId + ", result=" + result
				+ ", personId=" + personId + "]";
	}

	public int getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(int vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	public String getEngineNumber() {
		return engineNumber;
	}

	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}

	public String getChassisNumber() {
		return chassisNumber;
	}

	public void setChassisNumber(String chassisNumber) {
		this.chassisNumber = chassisNumber;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public Double getMileage() {
		return mileage;
	}

	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}

	public int getNoOfSeats() {
		return noOfSeats;
	}

	public void setNoOfSeats(int noOfSeats) {
		this.noOfSeats = noOfSeats;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}
}