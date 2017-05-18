package com.rg.service.bean.jsf;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.rg.service.bean.admin.MenuBean;
import com.rg.service.business.CommonService;
import com.rg.service.entity.Menu;
import com.rg.service.util.filter.FilterUtil;
import com.rg.service.util.filter.IPredicate;

@Named("menuDesignerController")
@SessionScoped
public class MenuDesignerControllerBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(MenuDesignerControllerBean.class.getName());

	@Inject
	private CommonService service;

	@Inject
	private MenuBean menuBean;

	public String addMoreSubMenu() {
		log.info("Entering addMoreSubMenu");
		Menu menu = new Menu();
		menu.setSubMenu(true);
		List<Menu> menus = menuBean.getMenu().getSubMenus();
		if (menus.isEmpty()) {
			menu.setMenuOrder(1);
		} else {
			Menu lastMenu = menus.get(menus.size() - 1);
			menu.setMenuOrder(lastMenu.getMenuOrder() + 1);
		}
		menus.add(menu);
		log.info("Exiting addMoreSubMenu");
		return "success";
	}

	public String deleteSubMenu(String index) {
		log.info("Entering deleteSubMenu");
		log.info("Going to delete index :: " + index);
		menuBean.getMenu().getSubMenus().remove(Integer.parseInt(index));
		log.info("Exiting deleteSubMenu");
		return "success";
	}

	public String addMenu() {
		log.info("Entering addMenu");
		boolean newlyAdded = false;
		if (menuBean.getMenu().getMenuId() == 0) {
			newlyAdded = true;
		}
		service.saveMenu(menuBean.getMenu());
		if (newlyAdded) {
			menuBean.getMenuList().add(menuBean.getMenu());
		}
		menuBean.setSelectedMenuId(0);
		log.info("Exiting addMenu");
		return "success";
	}

	public String editMenu(String menuId) {
		log.info("Entering editMenu");
		menuBean.setSelectedMenuId(Integer.parseInt(menuId));
		log.info("Selected Menu :: " + menuBean.getSelectedMenuId());
		IPredicate<Menu> menuFilter = new IPredicate<Menu>() {
			@Override
			public boolean apply(Menu type) {
				return type.getMenuId() == menuBean.getSelectedMenuId();
			}
		};
		Menu menu = FilterUtil.filter(menuBean.getMenuList(), menuFilter).get(0);
		menuBean.setMenu(menu);
		log.info("Selected Menu Bean :: " + menuBean.getMenu());
		log.info("Exiting editMenu");
		return "success";
	}

	public String deleteMenu(String menuId) {
		log.info("Entering deleteMenu");
		log.info("Selected Menu :: " + menuId);
		service.deleteMenu(Integer.parseInt(menuId));
		IPredicate<Menu> menuFilter = new IPredicate<Menu>() {
			@Override
			public boolean apply(Menu type) {
				return type.getMenuId() == Integer.parseInt(menuId);
			}
		};
		Menu menu = FilterUtil.filter(menuBean.getMenuList(), menuFilter).get(0);
		menuBean.getMenuList().remove(menu);
		log.info(menuBean.getMenuList().toString());
		if (menuBean.getMenuList().isEmpty()) {
			menuBean.setSelectedMenuId(-1);
			menuBean.setMenu(null);
		}
		log.info("Exiting deleteMenu");
		return "success";
	}

	public String addMenuPrepare() {
		log.info("Entering addMenuPrepare");
		menuBean.setSelectedMenuId(-1);
		menuBean.setMenu(null);
		log.info("Exiting addMenuPrepare");
		return "success";
	}

	public String saveMenuOrder() {
		log.info("Entering saveMenuOrder");
		List<Menu> ls = menuBean.getMenuList();
		service.saveMenuList(ls);
		menuBean.getMenuList().sort(Menu.MENU_COMPARATOR);
		log.info("Exiting saveMenuOrder");
		return "success";
	}

	public String cancelEdit() {
		log.info("Entering cancelEdit");
		menuBean.setSelectedMenuId(0);
		log.info("Exiting cancelEdit");
		return "success";
	}

	public CommonService getService() {
		return service;
	}

	public void setService(CommonService service) {
		this.service = service;
	}

	public MenuBean getMenuBean() {
		return menuBean;
	}

	public void setMenuBean(MenuBean menuBean) {
		this.menuBean = menuBean;
	}
}
