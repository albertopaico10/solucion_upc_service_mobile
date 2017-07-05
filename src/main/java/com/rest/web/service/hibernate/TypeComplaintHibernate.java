package com.rest.web.service.hibernate;

import java.util.List;

import com.rest.web.service.hibernate.bean.TypeComplaint;

public interface TypeComplaintHibernate {

	public List<TypeComplaint> listAllTypeComplaint()throws Exception;
	public TypeComplaint getTypeComplaint(int idTypeComplaint)throws Exception;
	
}
