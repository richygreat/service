package com.occar.dao;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.occar.entity.Attribute;
import com.occar.entity.Menu;
import com.occar.entity.Type;

@Named
@ApplicationScoped
public class CommonDAO implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final Logger log = Logger.getLogger(CommonDAO.class.getName());

	@PersistenceContext(unitName = "occardb")
	private EntityManager entityManager;

	public CommonDAO() {
		log.info("CommonDAO Constructor called");
	}
	
	@Transactional
	public Attribute getUniqueAttributeByKey(String key) {
		Attribute attr = null;
		log.info("Entering getUniqueAttributeByKey");
		try {
			attr = entityManager.createQuery("select a from Attribute a where a.key = '" + key + "'", Attribute.class)
					.getSingleResult();
		} catch (NoResultException e) {
			log.info("No Result found");
		} catch (NonUniqueResultException e) {
			log.info("Multiple Results found");
		}
		log.info("Exiting getUniqueAttributeByKey");
		return attr;
	}

	@Transactional
	public void saveAttribute(Attribute attr) {
		log.info("Entering saveAttribute");
		log.info("Persisting :: " + attr);
		if (attr.getAttributeId() == 0) {
			entityManager.persist(attr);
		} else {
			entityManager.merge(attr);
		}
		log.info("Saved AttrId :: " + attr.getAttributeId());
		log.info("Exiting saveAttribute");
	}

	@Transactional
	public List<Object> queryDB(String query) {
		List<Object> rs = null;
		log.info("Entering queryDB");
		rs = entityManager.createQuery(query, Object.class).getResultList();
		log.info("rs.size()::" + rs.size());
		log.info("Exiting queryDB");
		return rs;
	}

	@Transactional
	public List<Menu> getMenus() {
		List<Menu> menus = null;
		log.info("Entering getMenus");
		menus = entityManager.createQuery("select m from Menu m where m.subMenu = false", Menu.class).getResultList();
		log.info("Exiting getMenus");
		return menus;
	}

	@Transactional
	public void deleteMenu(int menuId) {
		log.info("Entering deleteMenu");
		Menu menu = entityManager.find(Menu.class, menuId);
		entityManager.remove(menu);
		log.info("Entering deleteMenu");
	}

	@Transactional
	public void saveMenu(Menu menu) {
		log.info("Entering saveMenu");
		int i = 1;
		for (Iterator<Menu> iterator = menu.getSubMenus().iterator(); iterator.hasNext();) {
			Menu menuBean = (Menu) iterator.next();
			if (menuBean.getName() != null && menuBean.getName().trim().length() != 0) {
				menuBean.setSubMenu(true);
				menuBean.setMenuOrder(i++);
			} else {
				iterator.remove();
			}
		}
		log.info("Persisting :: " + menu);
		if (menu.getMenuId() == 0) {
			entityManager.persist(menu);
		} else {
			entityManager.merge(menu);
		}
		log.info("Exiting saveMenu");
	}

	@Transactional
	public void saveMenuList(List<Menu> ls) {
		log.info("Entering saveMenuList");
		for (Menu menu : ls) {
			saveMenu(menu);
		}
		log.info("Exiting saveMenuList");
	}

	@Transactional
	public Type getTypeByDescription(String typeDescription) {
		log.info("Entering getTypeByDescription");
		Type type = entityManager
				.createQuery("select t from Type t where t.description = '" + typeDescription + "'", Type.class)
				.getSingleResult();
		log.info("Exiting getTypeByDescription");
		return type;
	}

	@Transactional
	public Type getTypeById(int typeId) {
		log.info("Entering getTypeById");
		Type type = entityManager.find(Type.class, typeId);
		log.info("Exiting getTypeById");
		return type;
	}

	@Transactional
	public List<Type> getTypes() {
		List<Type> types = null;
		log.info("Entering getTypes");
		types = entityManager.createQuery("select t from Type t where t.subType = false", Type.class).getResultList();
		log.info("Exiting getTypes");
		return types;
	}

	@Transactional
	public void deleteType(int typeId) {
		log.info("Entering deleteType");
		Type type = entityManager.find(Type.class, typeId);
		entityManager.remove(type);
		log.info("Entering deleteType");
	}

	@Transactional
	public void saveType(Type type) {
		log.info("Entering saveType");
		int i = 1;
		for (Iterator<Type> iterator = type.getSubTypes().iterator(); iterator.hasNext();) {
			Type typeBean = (Type) iterator.next();
			if (typeBean.getDescription() != null && typeBean.getDescription().trim().length() != 0) {
				typeBean.setSubType(true);
				typeBean.setTypeOrder(i++);
			} else {
				iterator.remove();
			}
		}
		log.info("Persisting :: " + type);
		if (type.getTypeId() == 0) {
			entityManager.persist(type);
		} else {
			entityManager.merge(type);
		}
		log.info("Exiting saveType");
	}

	@Transactional
	public void saveTypeList(List<Type> ls) {
		log.info("Entering saveTypeList");
		for (Type type : ls) {
			saveType(type);
		}
		log.info("Exiting saveTypeList");
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
}
