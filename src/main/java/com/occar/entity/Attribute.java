package com.occar.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Entity implementation class for Entity: Attributes
 *
 */
@Entity
public class Attribute implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int attributeId;
	@Column(name="attr")
	private String key;
	@Column(name="val")
	private String value;

	private static final long serialVersionUID = 1L;
	
	public Attribute() {
		super();
	}
	
	public Attribute(String key, String value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public String toString() {
		return "Attributes [attributeId=" + attributeId + ", key=" + key + ", value=" + value + "]";
	}

	public int getAttributeId() {
		return attributeId;
	}

	public void setAttributeId(int attributeId) {
		this.attributeId = attributeId;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}