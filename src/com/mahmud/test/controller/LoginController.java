package com.mahmud.test.controller;

import com.mahmud.test.Urls;
import com.mahmud.test.dto.LoginList;
import com.mahmud.test.model.LoginModel;
import com.mahmud.test.view.LoginView;
import com.mahmud.test.webservice.LoginSync;

public class LoginController 
{
	
	private LoginModel loginObj;
	private LoginView loginViewObj;
	/*
	 * In logincontroller constructor it's received two object one is receving values from view call.other is view class
	 * reference to update after operation is done.
	 */
	
	public LoginController(LoginModel loginObj,LoginView loginViewObj){
		this.loginObj=loginObj;
		this.loginViewObj=loginViewObj;
	}
	
	public void checkLoginInfo()
	{
		
			sendHttpRequest(Urls.URL_LOGIN,loginObj.getEmail(),loginObj.getPassword());
			
		
		
	}
	/*
	 * By this method we send url,with it's parameter as arg type.It's called a LoginSync abstract class.LoginSync
	 * communicate with server as asyncronisely.And send it's reply through callback way.
	 */

	private void sendHttpRequest(String... urls) 
	{
		
		
		LoginSync httpRequest=new LoginSync() {
			
			@Override
			public void postHttpResponse(LoginList responseData) {
				loginViewObj.updateView(responseData);
				
			}
			
			@Override
			public void postErrorResponse() {
				loginViewObj.updateView(null);
				
			}
		};
		httpRequest.execute(urls);

	}


}
