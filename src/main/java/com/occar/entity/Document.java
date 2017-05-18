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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.rg.service.entity.Attribute;
import com.rg.service.entity.Type;

/**
 * Entity implementation class for Entity: Document
 *
 */
@Entity
public class Document implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int documentId;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Person person;
	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	private Type type;
	private String fileName;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Attribute> attributes = new LinkedList<Attribute>();

	private static final long serialVersionUID = 1L;

	public Document() {
		super();
	}

	@Override
	public String toString() {
		return "Document [documentId=" + documentId + ", person=" + person + ", type=" + type + ", fileName=" + fileName
				+ ", attributes=" + attributes + "]";
	}

	public int getDocumentId() {
		return documentId;
	}

	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Type getType() {
		if (type == null) {
			type = new Type();
		}
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

}