package com.mahmud.test.dto;

import java.lang.reflect.Type;
import java.util.List;

import com.mahmud.test.model.Login;

public class LoginList implements Type {
	/*
	 * This class using after getting response from server we parse json data with gson.
	 * login_info is the array name in server response.
	 */
	private List<Login> login_info;
	public List<Login> getLogin(){
		return login_info;
	}
	public void setLoginList(List<Login> loginList){
		this.login_info=loginList;
		
	}

}
