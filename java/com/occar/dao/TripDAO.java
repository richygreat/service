package com.occar.dao;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.occar.entity.Person;
import com.occar.entity.Trip;

@Named
@SessionScoped
public class TripDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(TripDAO.class.getName());

	@PersistenceContext(unitName = "occardb")
	private EntityManager entityManager;

	public TripDAO() {
		log.info("TripDAO Constructor called");
	}

	@Transactional
	public int saveTrip(Trip trip) {
		log.info("Entering saveTrip");
		log.info("Persisting :: " + trip);
		if (trip.getTripId() == 0) {
			entityManager.persist(trip);
		} else {
			entityManager.merge(trip);
		}
		log.info("Exiting saveTrip");
		return trip.getTripId();
	}
	
	@Transactional
	public Trip getTripById(int tripId){
		log.info("Entering getTripById");
		Trip trip = entityManager.find(Trip.class, tripId);
		log.info("Exiting getTripById");
		return trip;
	}
	
	@Transactional
	public void saveTripWithPassenger(Trip trip, int passengerId) {
		log.info("Entering saveTripWithPassenger");
		Person passenger = entityManager.find(Person.class, passengerId);
		Trip tripNew = getTripById(trip.getTripId());
		tripNew.getPassengers().add(passenger);
		tripNew.setSeatsAvailable(trip.getSeatsAvailable());
		entityManager.merge(tripNew);
		log.info("Exiting saveTripWithPassenger");
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
