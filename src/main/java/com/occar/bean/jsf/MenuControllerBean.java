package com.occar.bean.jsf;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.occar.bean.admin.AppConfig;
import com.occar.bean.admin.DocumentBean;
import com.occar.bean.admin.MenuBean;
import com.occar.bean.admin.RouteBean;
import com.occar.bean.admin.TypeBean;
import com.occar.constant.CommonConstants;
import com.occar.entity.Menu;
import com.occar.entity.Person;
import com.occar.entity.Type;
import com.occar.service.CommonService;
import com.occar.service.PersonService;

@Named("menuController")
@SessionScoped
public class MenuControllerBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(MenuControllerBean.class.getName());

	private String function;

	@Inject
	private CommonService service;

	@Inject
	private PersonService personService;

	@Inject
	private MenuDesignerControllerBean menuDesignerController;

	@Inject
	private TypeDesignerControllerBean typeDesignerController;

	@Inject
	private DocumentControllerBean docControllerBean;

	@Inject
	private RouteControllerBean routeControllerBean;

	public String navigateToMenuDesign() {
		log.info("Entering navigateToMenuDesign");
		setFunction("menuDesign");
		MenuBean menuBean = menuDesignerController.getMenuBean();
		menuBean.setMenuList(service.getMenus());
		menuBean.getMenuList().sort(Menu.MENU_COMPARATOR);
		log.info(menuBean.getMenuList().toString());
		log.info("Exiting navigateToMenuDesign");
		return "success";
	}

	public String navigateToTypeDesign() {
		log.info("Entering navigateToTypeDesign");
		setFunction("typeDesign");
		TypeBean typeBean = typeDesignerController.getTypeBean();
		typeBean.setTypeList(service.getTypes());
		typeBean.getTypeList().sort(Type.TYPE_COMPARATOR);
		log.info(typeBean.getTypeList().toString());
		log.info("Exiting navigateToTypeDesign");
		return "success";
	}

	public String navigateToDocManager() {
		log.info("Entering navigateToDocManager");
		setFunction("docManage");
		DocumentBean docBean = docControllerBean.getDocBean();
		docBean.setDocTypes(service.getSubTypesByParentTypeDesc(CommonConstants.TYPE_DOCUMENTS));
		List<Person> inactivePersons = personService.getPersonsNotWithAttribute(AppConfig.ACTIVE_PERSON_ATTR_BEAN);
		docBean.setUserList(inactivePersons);
		log.info(docBean.toString());
		log.info("Exiting navigateToDocManager");
		return "success";
	}

	public String navigateToRouteManager() {
		log.info("Entering navigateToRouteManager");
		setFunction("routeManage");
		RouteBean routeBean = routeControllerBean.getRouteBean();
		routeBean.setRouteList(service.getRoutes());
		log.info(routeBean.getRouteList().toString());
		log.info("Exiting navigateToRouteManager");
		return "success";
	}

	public String getFunction() {
		return function;
	}

	public void setFunction(String function) {
		this.function = function;
	}

	public CommonService getService() {
		return service;
	}

	public void setService(CommonService service) {
		this.service = service;
	}

	public MenuDesignerControllerBean getMenuDesignerController() {
		return menuDesignerController;
	}

	public void setMenuDesignerController(MenuDesignerControllerBean menuDesignerController) {
		this.menuDesignerController = menuDesignerController;
	}

	public TypeDesignerControllerBean getTypeDesignerController() {
		return typeDesignerController;
	}

	public void setTypeDesignerController(TypeDesignerControllerBean typeDesignerController) {
		this.typeDesignerController = typeDesignerController;
	}

	public DocumentControllerBean getDocControllerBean() {
		return docControllerBean;
	}

	public void setDocControllerBean(DocumentControllerBean docControllerBean) {
		this.docControllerBean = docControllerBean;
	}

	public RouteControllerBean getRouteControllerBean() {
		return routeControllerBean;
	}

	public void setRouteControllerBean(RouteControllerBean routeControllerBean) {
		this.routeControllerBean = routeControllerBean;
	}

	public PersonService getPersonService() {
		return personService;
	}

	public void setPersonService(PersonService personService) {
		this.personService = personService;
	}
}
