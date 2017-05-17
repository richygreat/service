package com.occar.bean.admin;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.occar.entity.Document;
import com.occar.entity.Person;
import com.occar.entity.Type;

@Named
@SessionScoped
public class DocumentBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Document> docList;
	private Document doc;
	private int selectedDocId;
	private String selectedTypeDesc;
	private List<Type> docTypes;
	private Person user;
	private List<Person> userList;
	private int selectedPersonId;

	@Override
	public String toString() {
		return "DocumentBean [docList=" + docList + ", doc=" + doc + ", selectedDocId=" + selectedDocId
				+ ", selectedTypeDesc=" + selectedTypeDesc + ", docTypes=" + docTypes + ", user=" + user + ", userList="
				+ userList + ", selectedPersonId=" + selectedPersonId + "]";
	}

	public List<Document> getDocList() {
		return docList;
	}

	public void setDocList(List<Document> docList) {
		this.docList = docList;
	}

	public Document getDoc() {
		if (doc == null) {
			doc = new Document();
		}
		return doc;
	}

	public void setDoc(Document doc) {
		this.doc = doc;
	}

	public int getSelectedDocId() {
		return selectedDocId;
	}

	public void setSelectedDocId(int selectedDocId) {
		this.selectedDocId = selectedDocId;
	}

	public String getSelectedTypeDesc() {
		return selectedTypeDesc;
	}

	public void setSelectedTypeDesc(String selectedTypeDesc) {
		this.selectedTypeDesc = selectedTypeDesc;
	}

	public List<Type> getDocTypes() {
		return docTypes;
	}

	public void setDocTypes(List<Type> docTypes) {
		this.docTypes = docTypes;
	}

	public Person getUser() {
		if (selectedPersonId == 0 && userList != null && !userList.isEmpty()) {
			user = userList.get(0);
		}
		return user;
	}

	public void setUser(Person user) {
		this.user = user;
	}

	public List<Person> getUserList() {
		return userList;
	}

	public void setUserList(List<Person> userList) {
		this.userList = userList;
	}

	public int getSelectedPersonId() {
		return selectedPersonId;
	}

	public void setSelectedPersonId(int selectedPersonId) {
		this.selectedPersonId = selectedPersonId;
	}

}
