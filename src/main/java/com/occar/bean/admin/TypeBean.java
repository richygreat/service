package com.occar.bean.admin;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import com.occar.entity.Type;

@Named
@SessionScoped
public class TypeBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<Type> typeList;
	private Type type;
	private int selectedTypeId;

	public List<Type> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<Type> typeList) {
		this.typeList = typeList;
	}

	public Type getType() {
		if (type == null) {
			type = new Type();
			type.getSubTypes().add(new Type());
		}
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public int getSelectedTypeId() {
		return selectedTypeId;
	}

	public void setSelectedTypeId(int selectedTypeId) {
		this.selectedTypeId = selectedTypeId;
	}
}
