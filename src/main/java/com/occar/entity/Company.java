package com.occar.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 * Entity implementation class for Entity: Company
 *
 */
@Entity
public class Company implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int companyId;
	private String name;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Type type;

	private static final long serialVersionUID = 1L;

	public Company() {
		super();
	}

	@Override
	public String toString() {
		return "Company [companyId=" + companyId + ", name=" + name + ", type=" + type + "]";
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}