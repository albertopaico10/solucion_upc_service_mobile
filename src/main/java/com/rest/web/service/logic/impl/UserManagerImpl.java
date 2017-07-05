package com.rest.web.service.logic.impl;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.inmobile.canonical.scheduler.SchedulerSendEmailRequest;
import com.inmobile.canonical.user.UserRequest;
import com.inmobile.canonical.user.UserResponse;
import com.rest.web.service.bean.email.EmailBean;
import com.rest.web.service.bean.systemparam.SystemParamResponse;
import com.rest.web.service.hibernate.UserHibernate;
import com.rest.web.service.hibernate.bean.RequestResponse;
import com.rest.web.service.hibernate.bean.User;
import com.rest.web.service.logic.ReqRespManager;
import com.rest.web.service.logic.SystemParamManager;
import com.rest.web.service.logic.UserManager;
import com.rest.web.service.logic.UtilManager;
import com.rest.web.service.util.CommonConstants;
import com.rest.web.service.util.ConvertClass;
import com.rest.web.service.util.MailUtil;
import com.rest.web.service.util.UtilMethods;


@Service
@Transactional
public class UserManagerImpl implements UserManager {
	@Autowired
	private UserHibernate userHibernate;

	@Autowired
	private ReqRespManager reqRespManager;
	
	@Autowired
	private SystemParamManager systemParamManager;
	
	@Autowired
	private UtilManager utilManger;
	

	public UserResponse saveUserInformation(UserRequest beanRequest) {
		UserResponse beanUserResponse = new UserResponse();
		// --Save Json in Data Base
		RequestResponse valueReqResp = (RequestResponse) reqRespManager.saveOrUpdate(beanRequest,CommonConstants.TypeOperationReqResp.OPERATION_CREATE_USER,0, 0);
		System.out.println("ID Response : " + valueReqResp.getId());
		int idUser=0;
		try {
			boolean validateEmail=userHibernate.existEmail(beanRequest.getEmail());
			if(!validateEmail){
				User userDataBase=ConvertClass.convertUserRequestToDataBase(beanRequest);
				userDataBase.setStatus(1);
				userDataBase=userHibernate.saveUserResponseId(userDataBase);
				beanUserResponse.setIdUser(userDataBase.getId());
				//--Send Email
//				insertInScheduler(beanRequest.getEmail(),beanRequest.getLastNameUser()+", "+beanRequest.getNamesUser());
				//buidlEmailCreationUser(beanRequest.getEmail(),beanRequest.getLastNameUser()+", "+beanRequest.getNamesUser());
				//--Build Response for web service client
				beanUserResponse.setCodeResponse(CommonConstants.CodeResponse.CODE_RESPONSE_SUCCESS_USER);
				beanUserResponse.setMessagesResponse("The user was created successfully.");
			}else{
				beanUserResponse.setCodeResponse(CommonConstants.CodeResponse.CODE_RESPONSE_EXITS_USER);
				beanUserResponse.setMessagesResponse("The Email exist in our Data Base");
			}
			
		} catch (Exception e) {
			beanUserResponse.setCodeResponse(CommonConstants.CodeResponse.CODE_RESPONSE_ERROR);
			beanUserResponse.setMessagesResponse(e.getMessage());
		}
		//--Save Json in Data Base
		reqRespManager.saveOrUpdate(beanUserResponse, 
				CommonConstants.TypeOperationReqResp.OPERATION_CREATE_USER, beanUserResponse.getIdUser(),
				valueReqResp.getId());
		return beanUserResponse;
	}
	
	public UserResponse updateUserInformation(UserRequest beanRequest) {
		UserResponse beanUserResponse = new UserResponse();
		// --Save Json in Data Base
		RequestResponse valueReqResp = (RequestResponse) reqRespManager.saveOrUpdate(beanRequest,CommonConstants.TypeOperationReqResp.OPERATION_UPDATE_USER,0, 0);
		System.out.println("ID Response : " + valueReqResp.getId());
		int idUser=0;
		try {
//			boolean validateEmail=userHibernate.existEmail(beanRequest.getEmail());
//			if(!validateEmail){
			User userDataBase=ConvertClass.convertUserRequestToDataBase(beanRequest);
			System.out.println("El ID del usuario que se va actualizar : "+userDataBase.getId());
			userDataBase.setStatus(1);
			userHibernate.saveUserResponseId(userDataBase);
			beanUserResponse.setIdUser(userDataBase.getId());
				//--Send Email
//				insertInScheduler(beanRequest.getEmail(),beanRequest.getLastNameUser()+", "+beanRequest.getNamesUser());
//				buidlEmailCreationUser(beanRequest.getEmail(),beanRequest.getLastNameUser()+", "+beanRequest.getNamesUser());
				//--Build Response for web service client
				beanUserResponse.setCodeResponse(CommonConstants.CodeResponse.CODE_RESPONSE_SUCCESS_UPDATE_USER);
				beanUserResponse.setMessagesResponse("The user was updated successfully.");
//			}else{
//				beanUserResponse.setCodeResponse(CommonConstants.CodeResponse.CODE_RESPONSE_EXITS_USER);
//				beanUserResponse.setMessagesResponse("The Email exist in our Data Base");
//			}
			
		} catch (Exception e) {
			beanUserResponse.setCodeResponse(CommonConstants.CodeResponse.CODE_RESPONSE_ERROR);
			beanUserResponse.setMessagesResponse(e.getMessage());
		}
		//--Save Json in Data Base
		reqRespManager.saveOrUpdate(beanUserResponse, 
				CommonConstants.TypeOperationReqResp.OPERATION_UPDATE_USER, beanUserResponse.getIdUser(),
				valueReqResp.getId());
		return beanUserResponse;
	}
	
	public void insertInScheduler(String emailTo,String completeName){
		SchedulerSendEmailRequest beanSchedSendEmailReq=new SchedulerSendEmailRequest();
		beanSchedSendEmailReq.setEmailTo(emailTo);
		SystemParamResponse beanSystemParamResponse=systemParamManager.getSpecificSystemParam(CommonConstants.SystemParam.SYSTEM_PARAM_BODY_EMAIL_CREATION_USER);
		beanSchedSendEmailReq.setEmailBody(replaceValuesIntoEmailBodyUser(beanSystemParamResponse.getValueParam(), completeName));
		//--Save Information into Scheduler for send Email Later
		
	}
	public void buidlEmailCreationUser(String emilTo,String completeName)throws MessagingException{
		EmailBean beanEmailBean=null;
		if(utilManger.isSendEmailFromOtherAccount(emilTo)){
			beanEmailBean=systemParamManager.getEmailInSystemParamGmail(CommonConstants.Email.SYSTEM_PARAM_GENERAL_EMAIL,CommonConstants.Email.TYPE_OPERATION_CREATE_USER);
			beanEmailBean.setBodyEmail(replaceValuesIntoEmailBodyUser(beanEmailBean.getBodyEmail(), completeName));
			beanEmailBean.setToEmail(emilTo);
			MailUtil.sendEmail2(beanEmailBean);
		}else{
			beanEmailBean=systemParamManager.getEmailInSystemParam(CommonConstants.Email.SYSTEM_PARAM_GENERAL_EMAIL,CommonConstants.Email.TYPE_OPERATION_CREATE_USER);
			beanEmailBean.setBodyEmail(replaceValuesIntoEmailBodyUser(beanEmailBean.getBodyEmail(), completeName));
			beanEmailBean.setToEmail(emilTo);
			MailUtil.sendEmail(beanEmailBean);
		}
	}

	public UserResponse validateUser(UserRequest userRequest) {
		System.out.println("Entreeeeee validateUser");
		UserResponse userBeanResponse=new UserResponse();
		//--Save Json in Data Base
		RequestResponse valueReqResp=(RequestResponse)reqRespManager.saveOrUpdate(userRequest, 
				CommonConstants.TypeOperationReqResp.OPERATION_VALIDATE_USER, 0,0);
		System.out.println("ID Response : "+valueReqResp.getId());
		try {
			boolean validateEmail=userHibernate.existEmail(userRequest.getEmail());
			if(validateEmail){
				User userBean=userHibernate.validateUser(userRequest.getEmail(), userRequest.getPassword());
				if(userBean!=null){
					userBeanResponse.setIdUser(userBean.getId());
					userBeanResponse.setCodeResponse(CommonConstants.CodeResponse.CODE_RESPONSE_SUCCESS_VALIDATION);
					userBeanResponse.setMessagesResponse("The Email was validate correctly");
					userBeanResponse.setDescription(userBean.getNamesUser()+" "+userBean.getLastNameUser());
					userBeanResponse.setAdditional(userBean.getEmail());
					userBeanResponse.setTypeUser(userBean.getTypeUser());
				}else{
					userBeanResponse.setCodeResponse(CommonConstants.CodeResponse.CODE_RESPONSE_FAIL_VALIDATION);
					userBeanResponse.setMessagesResponse("Password is incorrect");
					userBeanResponse.setIdUser(9999);
				}
			}else{
				userBeanResponse.setCodeResponse(CommonConstants.CodeResponse.CODE_RESPONSE_NOT_EXITS_USER);
				userBeanResponse.setMessagesResponse("The email don't exist ins our Data Base");
			}
		} catch (Exception e) {
			userBeanResponse.setCodeResponse(CommonConstants.CodeResponse.CODE_RESPONSE_ERROR);
			userBeanResponse.setMessagesResponse(e.getMessage());
		}
		//--Save Json in Data Base
		reqRespManager.saveOrUpdate(userBeanResponse, 
				CommonConstants.TypeOperationReqResp.OPERATION_CREATE_USER, userBeanResponse.getIdUser(),
				valueReqResp.getId());
		return userBeanResponse;
	}
	
	public UserResponse getUserInformation(int idUser){
		UserResponse beanResponse=new UserResponse();
		RequestResponse valueReqResp = (RequestResponse) reqRespManager.saveOrUpdate(idUser,
				CommonConstants.TypeOperationReqResp.OPERATION_GET_USER_INFORMATION,0, 0);
		System.out.println("ID Response : " + valueReqResp.getId());
		try {
			User beanUser=userHibernate.getUser(String.valueOf(idUser));
			beanResponse = ConvertClass.convertUserToUserResponse(beanUser);
			beanResponse.setIdUser(idUser);
			beanResponse.setCodeResponse(CommonConstants.CodeResponse.CODE_RESPONSE_SUCCESS_GET_USER);
			beanResponse.setMessagesResponse("The customer's information was get successfully");	
			
		} catch (Exception e) {
			beanResponse.setCodeResponse(CommonConstants.CodeResponse.CODE_RESPONSE_ERROR);
			beanResponse.setMessagesResponse(e.getMessage());
		}
		reqRespManager.saveOrUpdate(beanResponse,CommonConstants.TypeOperationReqResp.OPERATION_GET_USER_INFORMATION,
				0,valueReqResp.getId());
		return beanResponse;
	}
	
	private String replaceValuesIntoEmailBodyUser(String emilTo,String completeName){
		emilTo=UtilMethods.getFinalValuesForEmail(emilTo, CommonConstants.Email.COMPLETE_NAME, String.valueOf(completeName));
		return emilTo;
	}

	public List<UserResponse> listAllTypeUser(int typeUser) {
		List<UserResponse> listResponse=new ArrayList<UserResponse>();
		try {
			List<User> listUser=userHibernate.listAllUserByType(typeUser);
			listResponse=ConvertClass.convertFromListUserToListUserResponse(listUser);
		} catch (Exception e) {
			System.out.println("Error : "+e.getMessage());
		}
		return listResponse;
	}
}
