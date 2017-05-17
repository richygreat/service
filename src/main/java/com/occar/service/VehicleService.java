package com.occar.service;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.occar.dao.VehicleDAO;
import com.occar.entity.Vehicle;

@Named
@SessionScoped
public class VehicleService implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private VehicleDAO dao;

	public Vehicle getVehicleById(int vehicleId) {
		return dao.getVehicleById(vehicleId);
	}

	public void deleteVehicle(int personId, int vehicleId) {
		dao.deleteVehicle(personId, vehicleId);
	}

	public void saveVehicle(Vehicle vehicle) {
		dao.saveVehicle(vehicle);
	}

	public void saveVehicleList(List<Vehicle> ls) {
		dao.saveVehicleList(ls);
	}

	public VehicleDAO getDao() {
		return dao;
	}

	public void setDao(VehicleDAO dao) {
		this.dao = dao;
	}
}