package com.rest.web.service.hibernate.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.rest.web.service.hibernate.ReqRespHibernate;
import com.rest.web.service.hibernate.bean.RequestResponse;

@Repository
public class ReqResHibernateImpl implements ReqRespHibernate {
	
	@Autowired
	SessionFactory sessionfactory;
	
	public Object saveOrUpdateReqResp(RequestResponse bean) throws Exception {
		Session session=sessionfactory.openSession();
		Transaction transaction=session.beginTransaction();
		session.saveOrUpdate(bean);
		System.out.println("Last ID : "+bean.getId());
		transaction.commit();
		session.close();
		return bean;
	}

	public RequestResponse findById(int id) throws Exception {
		String query="from RequestResponse where status=1 and id='"+id+"'";
		System.out.println("query : "+query);
		
		Session session=sessionfactory.openSession();
		
		List<RequestResponse> reqResponse=session.createQuery(query).list();
		System.out.println("Cantidad de filas : "+reqResponse.size());
		
		session.close();
		
		return reqResponse.get(0);
	}

	
	
}
