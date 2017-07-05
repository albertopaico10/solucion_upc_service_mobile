package com.rest.web.service.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.inmobile.canonical.user.UserRequest;
import com.inmobile.canonical.user.UserResponse;
import com.rest.web.service.logic.UserManager;
import com.rest.web.service.util.CommonConstants;


@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserManager userManager;

	@RequestMapping(value = CommonConstants.ValueRequestMapping.CREATE_USER, method = RequestMethod.POST)
	public @ResponseBody UserResponse createUser(@RequestBody UserRequest beanRequest) {
		logger.info("Start createEmployee.");
		logger.info("Correo : "+beanRequest.getEmail()+"** Password : "+beanRequest.getPassword());
		UserResponse beanResponse=userManager.saveUserInformation(beanRequest);
		
		return beanResponse;
	}
	
	@RequestMapping(value = CommonConstants.ValueRequestMapping.VALIDATE_USER, method = RequestMethod.POST)
	public @ResponseBody UserResponse validateUser(@RequestBody UserRequest beanRequest) {
		logger.info("Start validateUser.");
		logger.info("Correo : "+beanRequest.getEmail()+"** Password : "+beanRequest.getPassword());
		UserResponse beanResponse=userManager.validateUser(beanRequest);
		return beanResponse;
	}
	
	@RequestMapping(value = CommonConstants.ValueRequestMapping.UPDATE_USER, method = RequestMethod.POST)
	public @ResponseBody UserResponse updateUser(@RequestBody UserRequest beanRequest) {
		logger.info("Start updateEmployee.");
		logger.info("Correo : "+beanRequest.getEmail()+"** Password : "+beanRequest.getPassword());
		UserResponse beanResponse=userManager.updateUserInformation(beanRequest);
		
		return beanResponse;
	}
	
	@RequestMapping(value = CommonConstants.ValueRequestMapping.GET_USER, method = RequestMethod.GET)
	public @ResponseBody UserResponse getUserInformation(@PathVariable("idUser") int idUser) {
		logger.info("Start updateEmployee.");
		logger.info("ID User : "+idUser);
		UserResponse beanResponse=userManager.getUserInformation(idUser);
		
		return beanResponse;
	}
	
	@RequestMapping(value = CommonConstants.ValueRequestMapping.LIST_USER, method = RequestMethod.GET)
	public @ResponseBody List<UserResponse> listTypeUser(@PathVariable("typeUser") int typeUser) {
		logger.info("Start listTypeUser.");
		logger.info("Type User : "+typeUser);
		List<UserResponse> listResponse=userManager.listAllTypeUser(typeUser);
		
		return listResponse;
	}
	
}
