package com.rest.web.service.hibernate;

import com.rest.web.service.hibernate.bean.RequestResponse;

public interface ReqRespHibernate {

	public Object saveOrUpdateReqResp(RequestResponse bean)throws Exception;
	public RequestResponse findById(int id)throws Exception;
}
