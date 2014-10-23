package com.mahmud.test.webservice;

import com.google.gson.Gson;
import com.mahmud.test.IHttpLoginResponseListener;
import com.mahmud.test.IHttpResponseListener;
import com.mahmud.test.dto.LoginList;
import com.mahmud.test.dto.MovieList;
import com.mahmud.test.webservice.RestClient.RequestMethod;
import android.os.AsyncTask;
import android.util.Log;

public abstract class LoginSync extends AsyncTask<String, Integer, LoginList> implements IHttpLoginResponseListener
{
	private String EMAIL="email";
	private String PASSWORD="password";



	@Override
	protected LoginList doInBackground(String... urls) 
	{
		LoginList list=null;
		String Url = urls[0];
		String email=urls[1];		
		String password = urls[2];	
	
		
		RestClient restClientObj=new RestClient(Url,false);		
		restClientObj.AddParam(EMAIL, email);		
		restClientObj.AddParam(PASSWORD, password);
		try {
			restClientObj.Execute(RequestMethod.POST);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			postErrorResponse();
		}
		String json=restClientObj.getResponse();
		Log.v("Json response", json);
		
		list=new Gson().fromJson(json, LoginList.class);
		
		
		
		
		// TODO Auto-generated method stub
		return list;
	}
	
	@Override
	protected void onPostExecute(LoginList result) {
	
		
		if(result==null){
			postErrorResponse();
		}
		else{
			postHttpResponse(result);
		}
		
		
	
	}

}
