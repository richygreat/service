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

import com.occar.entity.Document;
import com.occar.entity.Person;

@Named
@SessionScoped
public class DocumentDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(DocumentDAO.class.getName());

	@PersistenceContext(unitName = "servicedb")
	private EntityManager entityManager;

	public DocumentDAO() {
		log.info("DocumentDAO Constructor called");
	}
	
	@Transactional
	public Document getDocumentById(int documentId){
		log.info("Entering getDocumentById");
		Document doc = entityManager.find(Document.class, documentId);
		log.info("Exiting getDocumentById");
		return doc;
	}

	@Transactional
	public List<Document> getDocumentByDescription(String typeDescription) {
		log.info("Entering getDocumentByDescription");
		List<Document> documents = entityManager
				.createQuery("select d from Document d where d.type in (select t from Type t where t.description = '"
						+ typeDescription + "')", Document.class)
				.getResultList();
		log.info("Exiting getDocumentByDescription");
		return documents;
	}

	@Transactional
	public Document deleteDocument(int docId, int personId) {
		log.info("Entering deleteDocument");
		Person person = entityManager.find(Person.class, personId);
		for (Iterator<Document> iterator = person.getDocuments().iterator(); iterator.hasNext();) {
			Document d = iterator.next();
			if (d.getDocumentId() == docId) {
				iterator.remove();
			}
		}
		entityManager.merge(person);
		Document doc = entityManager.find(Document.class, docId);
		entityManager.remove(doc);
		log.info("Exiting deleteDocument");
		return doc;
	}

	@Transactional
	public int saveDocument(Document doc) {
		log.info("Entering saveDocument");
		log.info("Persisting :: " + doc);
		if (doc.getDocumentId() == 0) {
			entityManager.persist(doc);
		} else {
			entityManager.merge(doc);
		}
		log.info("Exiting saveDocument");
		return doc.getDocumentId();
	}

	@Transactional
	public void saveDocumentList(List<Document> ls) {
		log.info("Entering saveDocumentList");
		for (Document doc : ls) {
			saveDocument(doc);
		}
		log.info("Exiting saveDocumentList");
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
