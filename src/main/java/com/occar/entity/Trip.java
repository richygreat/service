package com.occar.entity;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.OneToOne;

import com.rg.service.entity.Attribute;

/**
 * Entity implementation class for Entity: Trip
 *
 */
@Entity
public class Trip implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int tripId;
	private String description;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Location origin;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Location destination;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Route route;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Person driver;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private List<Person> passengers;
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private List<Location> stops = new LinkedList<Location>();
	private Date tripDate;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Attribute> attributes = new LinkedList<Attribute>();
	private int seatsAvailable;

	private static final long serialVersionUID = 1L;

	public Trip() {
		super();
	}

	@Override
	public String toString() {
		return "Trip [tripId=" + tripId + ", description=" + description + ", origin=" + origin + ", destination="
				+ destination + ", route=" + route + ", driver=" + driver + ", passengers=" + passengers + ", stops="
				+ stops + ", tripDate=" + tripDate + ", attributes=" + attributes + ", seatsAvailable=" + seatsAvailable
				+ "]";
	}

	public int getTripId() {
		return tripId;
	}

	public void setTripId(int tripId) {
		this.tripId = tripId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Location getOrigin() {
		return origin;
	}

	public void setOrigin(Location origin) {
		this.origin = origin;
	}

	public Location getDestination() {
		return destination;
	}

	public void setDestination(Location destination) {
		this.destination = destination;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Person getDriver() {
		return driver;
	}

	public void setDriver(Person driver) {
		this.driver = driver;
	}

	public List<Person> getPassengers() {
		return passengers;
	}

	public void setPassengers(List<Person> passengers) {
		this.passengers = passengers;
	}

	public List<Location> getStops() {
		return stops;
	}

	public void setStops(List<Location> stops) {
		this.stops = stops;
	}

	public Date getTripDate() {
		return tripDate;
	}

	public void setTripDate(Date tripDate) {
		this.tripDate = tripDate;
	}

	public List<Attribute> getAttributes() {
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

	public int getSeatsAvailable() {
		return seatsAvailable;
	}

	public void setSeatsAvailable(int seatsAvailable) {
		this.seatsAvailable = seatsAvailable;
	}

}