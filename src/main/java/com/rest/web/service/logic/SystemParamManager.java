package com.rest.web.service.logic;

import java.util.List;

import com.rest.web.service.bean.email.EmailBean;
import com.rest.web.service.bean.systemparam.SystemParamResponse;

public interface SystemParamManager {

	public List<SystemParamResponse> listSpecificSystemParam(String generalParam);
	public EmailBean getEmailInSystemParam(String generalParam,String typeOperation);
	public EmailBean getEmailInSystemParamGmail(String generalParam,String typeOperation);
	public SystemParamResponse getSpecificSystemParam(String generalParam);
}
