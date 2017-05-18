package com.rg.service.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * Entity implementation class for Entity: Type
 *
 */
@Entity
public class Type implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int typeId;
	private String type;
	private String description;
	private boolean subType;
	private Integer typeOrder;

	private static final long serialVersionUID = 1L;

	@Transient
	public static final TypeComparator TYPE_COMPARATOR = new TypeComparator();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Type> subTypes = new LinkedList<Type>();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Attribute> attributes = new LinkedList<Attribute>();

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isSubType() {
		return subType;
	}

	public void setSubType(boolean subType) {
		this.subType = subType;
	}

	public Integer getTypeOrder() {
		if (typeOrder == null) {
			typeOrder = 0;
		}
		return typeOrder;
	}

	public void setTypeOrder(Integer typeOrder) {
		this.typeOrder = typeOrder;
	}

	public List<Type> getSubTypes() {
		if (subTypes == null) {
			subTypes = new LinkedList<Type>();
		}
		subTypes.sort(TYPE_COMPARATOR);
		return subTypes;
	}

	public void setSubTypes(List<Type> subTypes) {
		this.subTypes = subTypes;
	}

	public String getSubTypesDisplay() {
		StringBuilder sb = new StringBuilder();
		List<Type> ls = getSubTypes();
		for (Type m : ls) {
			sb.append(m.getDescription()).append("\n");
		}
		return sb.toString();
	}

	public List<Attribute> getAttributes() {
		if (attributes == null) {
			attributes = new LinkedList<Attribute>();
		}
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
	
	public Attribute getAttributeByKey(String key) {
		for (Attribute attribute : attributes) {
			if(key.equals(attribute.getKey())) {
				return attribute;
			}
		}
		return null;
	}

}

class TypeComparator implements Comparator<Type> {

	@Override
	public int compare(Type o1, Type o2) {
		return o1.getTypeOrder().compareTo(o2.getTypeOrder());
	}

}