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
 * Entity implementation class for Entity: Menu
 *
 */
@Entity
public class Menu implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int menuId;
	private String name;
	private String cssClass;
	private String htmlId;
	private String url;
	private String onClick;
	private String toolTip;
	private boolean subMenu;
	private Integer menuOrder;

	private static final long serialVersionUID = 1L;

	@Transient
	public static final MenuComparator MENU_COMPARATOR = new MenuComparator();

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Menu> subMenus = new LinkedList<Menu>();

	@Override
	public String toString() {
		return "Menu [menuId=" + menuId + ", name=" + name + ", url=" + url
				+ ", subMenu=" + subMenu + ", subMenus=" + subMenus
				+ ", menuOrder=" + menuOrder + "]";
	}

	public Menu() {
		super();
	}

	public int getMenuId() {
		return this.menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCssClass() {
		return this.cssClass;
	}

	public void setCssClass(String cssClass) {
		this.cssClass = cssClass;
	}

	public String getHtmlId() {
		return this.htmlId;
	}

	public void setHtmlId(String htmlId) {
		this.htmlId = htmlId;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOnClick() {
		return this.onClick;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

	public String getToolTip() {
		return this.toolTip;
	}

	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}

	public List<Menu> getSubMenus() {
		if (subMenus == null) {
			subMenus = new LinkedList<Menu>();
		}
		subMenus.sort(MENU_COMPARATOR);
		return subMenus;
	}
	
	public String getSubMenusDisplay() {
		StringBuilder sb = new StringBuilder();
		List<Menu> ls = getSubMenus();
		for (Menu m : ls) {
			sb.append(m.getName()).append("\n");
		}
		return sb.toString();
	}

	public void setSubMenus(List<Menu> subMenus) {
		this.subMenus = subMenus;
	}

	public boolean isSubMenu() {
		return subMenu;
	}

	public void setSubMenu(boolean subMenu) {
		this.subMenu = subMenu;
	}

	public Integer getMenuOrder() {
		if (menuOrder == null) {
			menuOrder = 0;
		}
		return menuOrder;
	}

	public void setMenuOrder(Integer menuOrder) {
		this.menuOrder = menuOrder;
	}

}

class MenuComparator implements Comparator<Menu> {

	@Override
	public int compare(Menu o1, Menu o2) {
		return o1.getMenuOrder().compareTo(o2.getMenuOrder());
	}

}