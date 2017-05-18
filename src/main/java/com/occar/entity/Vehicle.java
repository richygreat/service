package com.occar.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.rg.service.entity.Type;

/**
 * Entity implementation class for Entity: Vehicle
 *
 */
@Entity
public class Vehicle implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int vehicleId;
	private String regNumber;
	private String engineNumber;
	private String chassisNumber;
	private String fuelType;
	private Double mileage;
	private int noOfSeats;
	private int rank;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Type type;

	private static final long serialVersionUID = 1L;

	public Vehicle() {
		super();
	}

	@Override
	public String toString() {
		return "Vehicle [vehicleId=" + vehicleId + ", regNumber=" + regNumber + ", engineNumber=" + engineNumber
				+ ", chassisNumber=" + chassisNumber + ", fuelType=" + fuelType + ", mileage=" + mileage
				+ ", noOfSeats=" + noOfSeats + ", rank=" + rank + ", type=" + type + "]";
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

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

}