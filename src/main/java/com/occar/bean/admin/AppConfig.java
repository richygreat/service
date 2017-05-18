package com.occar.bean.admin;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.rg.service.constant.CommonConstants;
import com.rg.service.dao.CommonDAO;
import com.rg.service.entity.Attribute;
import com.rg.service.entity.Menu;
import com.rg.service.entity.Type;

@Named("appConfigBean")
@ApplicationScoped
public class AppConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(AppConfig.class.getName());

	private List<Menu> menuList;
	private List<Type> typeList;
	public static Attribute ACTIVE_PERSON_ATTR_BEAN;
	public static Attribute GOOGLE_CAR_STOP_NAME_ATTR_BEAN;

	@Inject
	private CommonDAO dao;

	@PostConstruct
	public void postConstruct() {
		log.info("===========================AppConfig===========================");
		Attribute activePersonAttr = dao.getUniqueAttributeByKey(CommonConstants.ATTR_PERSON_ACTIVE);
		if (activePersonAttr != null) {
			log.info(activePersonAttr.toString());
		} else {
			activePersonAttr = new Attribute(CommonConstants.ATTR_PERSON_ACTIVE, "true");
			dao.saveAttribute(activePersonAttr);
		}
		ACTIVE_PERSON_ATTR_BEAN = activePersonAttr;
		
		Attribute googleCarStopNameAttr = dao.getUniqueAttributeByKey(CommonConstants.GOOGLE_CAR_STOP_NAME);
		if (googleCarStopNameAttr != null) {
			log.info(googleCarStopNameAttr.toString());
		} else {
			googleCarStopNameAttr = new Attribute(CommonConstants.GOOGLE_CAR_STOP_NAME, "OCCarAppStops1");
			dao.saveAttribute(googleCarStopNameAttr);
		}
		GOOGLE_CAR_STOP_NAME_ATTR_BEAN = googleCarStopNameAttr;
	}

	public void dummy() {
	}

	public String refresh() {
		menuList = null;
		typeList = null;
		return "success";
	}

	public List<Menu> getMenuList() {
		if (menuList == null || menuList.isEmpty()) {
			menuList = dao.getMenus();
		}
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	public List<Type> getTypeList() {
		if (typeList == null || typeList.isEmpty()) {
			typeList = dao.getTypes();
		}
		return typeList;
	}

	public void setTypeList(List<Type> typeList) {
		this.typeList = typeList;
	}

	public CommonDAO getDao() {
		return dao;
	}

	public void setDao(CommonDAO dao) {
		this.dao = dao;
	}

}
