package com.occar.dao;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.occar.entity.Person;
import com.occar.entity.Vehicle;

@Named
@SessionScoped
public class VehicleDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(VehicleDAO.class.getName());

	@PersistenceContext(unitName = "servicedb")
	private EntityManager entityManager;

	public VehicleDAO() {
		log.info("VehicleDAO Constructor called");
	}

	@Transactional
	public Vehicle getVehicleById(int vehicleId) {
		log.info("Entering getVehicleByDescription");
		Vehicle vehicle = entityManager.find(Vehicle.class, vehicleId);
		log.info("Exiting getVehicleByDescription");
		return vehicle;
	}

	@Transactional
	public void deleteVehicle(int personId, int vehicleId) {
		log.info("Entering deleteVehicle");
		Person person = entityManager.find(Person.class, personId);
		for (Iterator<Vehicle> iterator = person.getVehicles().iterator(); iterator.hasNext();) {
			Vehicle v = iterator.next();
			if (v.getVehicleId() == vehicleId) {
				iterator.remove();
			}
		}
		entityManager.merge(person);
		Vehicle vehicle = entityManager.find(Vehicle.class, vehicleId);
		entityManager.remove(vehicle);
		log.info("Entering deleteVehicle");
	}

	@Transactional
	public void saveVehicle(Vehicle vehicle) {
		log.info("Entering saveVehicle");
		log.info("Persisting :: " + vehicle);
		if (vehicle.getVehicleId() == 0) {
			entityManager.persist(vehicle);
		} else {
			entityManager.merge(vehicle);
		}
		log.info("Exiting saveVehicle");
	}

	@Transactional
	public void saveVehicleList(List<Vehicle> ls) {
		log.info("Entering saveVehicleList");
		for (Vehicle vehicle : ls) {
			saveVehicle(vehicle);
		}
		log.info("Exiting saveVehicleList");
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
