package com.rest.web.service.util;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import com.inmobile.canonical.complaint.ComplientRequest;
import com.inmobile.canonical.complaint.ComplientResponse;
import com.inmobile.canonical.complaint.ListComplaintResponse;
import com.inmobile.canonical.complaint.TypeComplaintResponse;
import com.inmobile.canonical.image.ImageRequest;
import com.inmobile.canonical.image.ImageResponse;
import com.inmobile.canonical.scheduler.SchedulerSendEmailRequest;
import com.inmobile.canonical.user.UserRequest;
import com.inmobile.canonical.user.UserResponse;
import com.inmobile.canonical.util.Constants;
import com.rest.web.service.bean.systemparam.SystemParamResponse;
import com.rest.web.service.bean.ubigeo.Ubigeo;
import com.rest.web.service.hibernate.TypeComplaintHibernate;
import com.rest.web.service.hibernate.bean.SystemParam;
import com.rest.web.service.hibernate.bean.TypeComplaint;
import com.rest.web.service.hibernate.bean.User;

public class ConvertClass {

	public static User convertUserRequestToDataBase(UserRequest beanRequest){
		User userDataBase=new User();
		if(beanRequest.getId()!=null){
			userDataBase.setId(Integer.parseInt(beanRequest.getId()));	
		}
		userDataBase.setEmail(beanRequest.getEmail());
		userDataBase.setPasswordUser(beanRequest.getPassword());
		userDataBase.setTypeUser(Integer.parseInt(beanRequest.getTypeCustomer()));
		userDataBase.setNamesUser(beanRequest.getNamesUser());
		userDataBase.setLastNameUser(beanRequest.getLastNameUser());
		userDataBase.setDniUser(beanRequest.getDniUser());
		userDataBase.setRecordingDevice(beanRequest.getRecordingDevice());
		return userDataBase;
	}
	
	public static UserResponse convertUserToUserResponse(User beanUser){
		UserResponse beanResponse=new UserResponse();
		beanResponse.setEmail(beanUser.getEmail());
		beanResponse.setPassword(beanUser.getPasswordUser());
		beanResponse.setNameUser(beanUser.getNamesUser());
		beanResponse.setLastNameUser(beanUser.getLastNameUser());
		beanResponse.setDniUser(beanUser.getDniUser());
		return beanResponse;
	}
		
	public static List<TypeComplaintResponse> convertDataBaseToTypeComplaint(List<TypeComplaint> listTypeComplaint){
		List<TypeComplaintResponse> listTypeComplaintResponse=new ArrayList<TypeComplaintResponse>();
		for(TypeComplaint beanTypeComplaint:listTypeComplaint){
			TypeComplaintResponse beanTypeComplaintResponse=new TypeComplaintResponse();
			beanTypeComplaintResponse.setId(String.valueOf(beanTypeComplaint.getId()));
			beanTypeComplaintResponse.setNameComplaint(beanTypeComplaint.getCategoryComplaint());
			listTypeComplaintResponse.add(beanTypeComplaintResponse);
		}
		return listTypeComplaintResponse;
	}
	
	public static SystemParamResponse convertFromDataBaseToSystemParamResponse(SystemParam beanSystemParam){
		SystemParamResponse beanSystemParamResponse=new SystemParamResponse();
		beanSystemParamResponse.setId(beanSystemParam.getId());
		beanSystemParamResponse.setNameParam(beanSystemParam.getNameParam());
		beanSystemParamResponse.setReasonParam(beanSystemParam.getReasonParam());
		beanSystemParamResponse.setValueParam(beanSystemParam.getValueParam());
		beanSystemParamResponse.setStatus(beanSystemParam.getStatus());
		return beanSystemParamResponse;
	}
		
	public static List<UserResponse> convertFromListUserToListUserResponse(List<User> listUser){
		List<UserResponse> listResponse=new ArrayList<UserResponse>();
		for(User beanUser:listUser){
			UserResponse responseBean=convertUserToUserResponse(beanUser);
			responseBean.setIdUser(beanUser.getId());
			listResponse.add(responseBean);
		}
		return listResponse;
	}
}
