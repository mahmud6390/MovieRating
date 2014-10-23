package com.mahmud.test.webservice;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.mahmud.test.IHttpResponseListener;
import com.mahmud.test.dto.MovieList;
import com.mahmud.test.dto.MoviePictureList;
import com.mahmud.test.model.MovieModel;
import com.mahmud.test.model.MoviePictureModel;
import com.mahmud.test.webservice.RestClient.RequestMethod;
import android.os.AsyncTask;
import android.util.Log;

public abstract class MoviePictureListSync extends AsyncTask<String, Integer, MoviePictureList> implements IHttpResponseListener
{
	ArrayList<MoviePictureModel> moviePictureList;

	@Override
	protected MoviePictureList doInBackground(String... urls) 
	{
		MoviePictureList list=null;
		String url=urls[0];
		
		Log.v("URL>>>>>", url+"");
		
		RestClient restClientObj=new RestClient(url,false);
		try {
			restClientObj.Execute(RequestMethod.GET);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			postErrorResponse();
		}
		String json=restClientObj.getResponse();
		Log.v("server response", json);
		list=new Gson().fromJson(json, MoviePictureList.class);
		
		// TODO Auto-generated method stub
		return list;
	}
	@Override
	protected void onPostExecute(MoviePictureList result) {
		if(result==null){
			postErrorResponse();
		}
		else{
			
		moviePictureList=new ArrayList<MoviePictureModel>();
		
		for(MoviePictureModel cm:result.getContact())
		{
			moviePictureList.add(cm);
		}
		Log.v("list size", moviePictureList.size()+"");
		postHttpResponse(moviePictureList);
		
		}
	
	}

}
