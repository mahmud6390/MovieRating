
package com.mahmud.test;

import java.util.List;

import com.mahmud.test.dto.LoginList;

/**
 * IHttpLoginResponseListener is an interface class to using the post message after
 * getting result from server.
 * 
 * @author Mahmud
 * 
 */
public interface IHttpLoginResponseListener {
	/**
	 * This method call when it receive the server response data and pass the
	 * callback to it's parent class.
	 * 
	 * @param responseData
	 *            server response data
	 */
	
	public abstract void postHttpResponse(LoginList responseData);
	/**
	 * This method use to callback the server response to provisioner class.If
	 * server response is null or any exception occured this method fire the
	 * callback.
	 */
	public abstract void postErrorResponse();

}
