package com.rest.web.service.logic.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rest.web.service.bean.systemparam.SystemParamResponse;
import com.rest.web.service.logic.SystemParamManager;
import com.rest.web.service.logic.UtilManager;
import com.rest.web.service.util.CommonConstants;

@Service
@Transactional
public class UtilManagerImpl implements UtilManager{

	@Autowired
	private SystemParamManager systemParamManager;
	
	public boolean isSendEmailFromOtherAccount(String email){
		boolean value=false;
		List<SystemParamResponse> listSystemParamResponse=systemParamManager.listSpecificSystemParam(CommonConstants.SystemParam.SYSTEM_PARAM_SEND_EMAIL);
		for(SystemParamResponse beanSystemParamResponse:listSystemParamResponse){
			if(email.endsWith(beanSystemParamResponse.getValueParam())){
				value=true;
				break;
			}
		}
		return value;
	}
	
}
