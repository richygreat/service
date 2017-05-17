package com.occar.bean.jsf;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.occar.bean.admin.TypeBean;
import com.occar.entity.Type;
import com.occar.service.CommonService;
import com.occar.util.filter.FilterUtil;
import com.occar.util.filter.IPredicate;

@Named("typeDesignerController")
@SessionScoped
public class TypeDesignerControllerBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(TypeDesignerControllerBean.class.getName());

	@Inject
	private CommonService service;

	@Inject
	private TypeBean typeBean;

	public String addMoreSubType() {
		log.info("Entering addMoreSubType");
		Type type = new Type();
		type.setSubType(true);
		List<Type> types = typeBean.getType().getSubTypes();
		if (types.isEmpty()) {
			type.setTypeOrder(1);
		} else {
			Type lastType = types.get(types.size() - 1);
			type.setTypeOrder(lastType.getTypeOrder() + 1);
		}
		types.add(type);
		log.info("Exiting addMoreSubType");
		return "success";
	}

	public String deleteSubType(String index) {
		log.info("Entering deleteSubType");
		log.info("Going to delete index :: " + index);
		typeBean.getType().getSubTypes().remove(Integer.parseInt(index));
		log.info("Exiting deleteSubType");
		return "success";
	}

	public String addType() {
		log.info("Entering addType");
		boolean newlyAdded = false;
		if (typeBean.getType().getTypeId() == 0) {
			newlyAdded = true;
		}
		service.saveType(typeBean.getType());
		if (newlyAdded) {
			typeBean.getTypeList().add(typeBean.getType());
		}
		typeBean.setSelectedTypeId(0);
		log.info("Exiting addType");
		return "success";
	}

	public String editType(String typeId) {
		log.info("Entering editType");
		typeBean.setSelectedTypeId(Integer.parseInt(typeId));
		log.info("Selected Type :: " + typeBean.getSelectedTypeId());
		IPredicate<Type> typeFilter = new IPredicate<Type>() {
			@Override
			public boolean apply(Type type) {
				return type.getTypeId() == typeBean.getSelectedTypeId();
			}
		};
		Type type = FilterUtil.filter(typeBean.getTypeList(), typeFilter).get(0);
		typeBean.setType(type);
		log.info("Selected Type Bean :: " + typeBean.getType());
		log.info("Exiting editType");
		return "success";
	}

	public String deleteType(String typeId) {
		log.info("Entering deleteType");
		log.info("Selected Type :: " + typeId);
		service.deleteType(Integer.parseInt(typeId));
		IPredicate<Type> typeFilter = new IPredicate<Type>() {
			@Override
			public boolean apply(Type type) {
				return type.getTypeId() == Integer.parseInt(typeId);
			}
		};
		Type type = FilterUtil.filter(typeBean.getTypeList(), typeFilter).get(0);
		typeBean.getTypeList().remove(type);
		log.info(typeBean.getTypeList().toString());
		if (typeBean.getTypeList().isEmpty()) {
			typeBean.setSelectedTypeId(-1);
			typeBean.setType(null);
		}
		log.info("Exiting deleteType");
		return "success";
	}

	public String addTypePrepare() {
		log.info("Entering addTypePrepare");
		typeBean.setSelectedTypeId(-1);
		typeBean.setType(null);
		log.info("Exiting addTypePrepare");
		return "success";
	}

	public String saveTypeOrder() {
		log.info("Entering saveTypeOrder");
		List<Type> ls = typeBean.getTypeList();
		service.saveTypeList(ls);
		typeBean.getTypeList().sort(Type.TYPE_COMPARATOR);
		log.info("Exiting saveTypeOrder");
		return "success";
	}

	public String cancelEdit() {
		log.info("Entering cancelEdit");
		typeBean.setSelectedTypeId(0);
		log.info("Exiting cancelEdit");
		return "success";
	}

	public CommonService getService() {
		return service;
	}

	public void setService(CommonService service) {
		this.service = service;
	}

	public TypeBean getTypeBean() {
		return typeBean;
	}

	public void setTypeBean(TypeBean typeBean) {
		this.typeBean = typeBean;
	}
}
