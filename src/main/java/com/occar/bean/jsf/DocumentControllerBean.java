package com.occar.bean.jsf;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.occar.bean.admin.AppConfig;
import com.occar.bean.admin.DocumentBean;
import com.occar.entity.Person;
import com.occar.service.PersonService;

@Named("documentController")
@SessionScoped
public class DocumentControllerBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(DocumentControllerBean.class.getName());

	@Inject
	private PersonService personService;

	@Inject
	private DocumentBean docBean;

	public String approveUser(String personId) {
		log.info("Entering approve user");
		Person person = getPersonById(personId);
		person.getAttributes().add(AppConfig.ACTIVE_PERSON_ATTR_BEAN);
		person.setRejectionReason(null);
		personService.savePerson(person);
		List<Person> users = docBean.getUserList();
		users.removeIf(u -> personId.equals(String.valueOf(u.getPersonId())));
		log.info("Exiting approve user");
		return "success";
	}

	public String rejectUser(String personId) {
		log.info("Entering reject user");
		Person person = getPersonById(personId);
		log.info("Comment :: " + person.getRejectionReason());
		personService.savePerson(person);
		log.info("Exiting reject user");
		return "success";
	}

	/**
	 * Searches using Streams API Java 8
	 * 
	 * @param personId
	 * @return
	 */
	private Person getPersonById(String personId) {
		List<Person> users = docBean.getUserList();
		Optional<Person> searchedPerson = users.stream().filter(u -> personId.equals(String.valueOf(u.getPersonId())))
				.findFirst();
		Person person = searchedPerson.get();
		log.info("Person found in userList:: " + person.toString());
		return person;
	}

	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}

	public DocumentBean getDocBean() {
		return docBean;
	}

	public void setDocBean(DocumentBean docBean) {
		this.docBean = docBean;
	}
}
