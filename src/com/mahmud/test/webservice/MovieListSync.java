package com.mahmud.test.webservice;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.mahmud.test.IHttpResponseListener;
import com.mahmud.test.dto.MovieList;
import com.mahmud.test.model.MovieModel;
import com.mahmud.test.webservice.RestClient.RequestMethod;
import android.os.AsyncTask;
import android.util.Log;

public abstract class MovieListSync extends AsyncTask<String, Integer, MovieList> implements IHttpResponseListener
{
	ArrayList<MovieModel> movieList;

	@Override
	protected MovieList doInBackground(String... urls) 
	{
		MovieList list=null;
		String getMutualUrl=urls[0];
		Log.v("URL>>>>>", getMutualUrl+"");
		
		RestClient restClientObj=new RestClient(getMutualUrl,false);
		try {
			restClientObj.Execute(RequestMethod.GET);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			postErrorResponse();
		}
		String json=restClientObj.getResponse();
		Log.v("server response", json);
		list=new Gson().fromJson(json, MovieList.class);
		
		// TODO Auto-generated method stub
		return list;
	}
	@Override
	protected void onPostExecute(MovieList result) {
		if(result==null){
			postErrorResponse();
		}
		else{
			
		movieList=new ArrayList<MovieModel>();
		
		for(MovieModel cm:result.getContact())
		{
			movieList.add(cm);
		}
		Log.v("list size", movieList.size()+"");
		postHttpResponse(movieList);
		
		}
	
	}

}
