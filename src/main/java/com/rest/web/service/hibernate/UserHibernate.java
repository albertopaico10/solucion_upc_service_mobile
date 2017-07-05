package com.rest.web.service.hibernate;

import java.util.List;

import com.rest.web.service.hibernate.bean.User;

public interface UserHibernate {
	public User saveUserResponseId(User userBean)throws Exception;
	public boolean existEmail(String email)throws Exception;
	public User validateUser(String email,String password)throws Exception;
	public User getUser(String idUser) throws Exception;
	public List<User> listAllUserByType(int typeUser)throws Exception;
}
