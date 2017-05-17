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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * Entity implementation class for Entity: Route
 *
 */
@Entity
public class Route implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int routeId;
	private String name;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Location origin;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Location destination;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Attribute> attributes = new LinkedList<Attribute>();
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private List<Location> stops = new LinkedList<Location>();

	private static final long serialVersionUID = 1L;

	public Route() {
		super();
	}

	@Override
	public String toString() {
		return "Route [routeId=" + routeId + ", name=" + name + ", origin=" + origin + ", destination=" + destination
				+ ", attributes=" + attributes + ", stops=" + stops + "]";
	}

	public int getRouteId() {
		return routeId;
	}

	public void setRouteId(int routeId) {
		this.routeId = routeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public List<Location> getStops() {
		return stops;
	}

	public void setStops(List<Location> stops) {
		this.stops = stops;
	}
}