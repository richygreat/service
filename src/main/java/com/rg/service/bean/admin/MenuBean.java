package com.rg.service.bean.admin;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.rg.service.entity.Menu;

@Named
@SessionScoped
public class MenuBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Menu> menuList;
	private Menu menu;
	private int selectedMenuId;

	public List<Menu> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Menu> menuList) {
		this.menuList = menuList;
	}

	public Menu getMenu() {
		if (menu == null) {
			menu = new Menu();
			menu.getSubMenus().add(new Menu());
		}
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public int getSelectedMenuId() {
		return selectedMenuId;
	}

	public void setSelectedMenuId(int selectedMenuId) {
		this.selectedMenuId = selectedMenuId;
	}
}
