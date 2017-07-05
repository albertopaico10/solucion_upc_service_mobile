package com.rest.web.service.logic;

import java.util.List;

import javax.mail.MessagingException;

import org.springframework.stereotype.Service;

import com.inmobile.canonical.user.UserRequest;
import com.inmobile.canonical.user.UserResponse;

@Service
public interface UserManager {
	
	public UserResponse saveUserInformation(UserRequest beanRequest);
	public UserResponse validateUser(UserRequest userRequest);
	public void buidlEmailCreationUser(String emilTo,String completeName)throws MessagingException;
	public UserResponse updateUserInformation(UserRequest beanRequest);
	public UserResponse getUserInformation(int idUser);
	public List<UserResponse> listAllTypeUser(int typeUser);
}
