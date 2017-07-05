package com.rest.web.service.hibernate;

import java.util.List;

import com.rest.web.service.hibernate.bean.SystemParam;

public interface SystemParamHibernate {
	public List<SystemParam> listsSpecificSystemParam(List<String> listValues);
	public List<SystemParam> listsByParam(String generalParam);
	public List<SystemParam> listsByNameParam(String nameParam);
}
