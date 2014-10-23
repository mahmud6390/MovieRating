package com.mahmud.test.webservice;

import com.mahmud.test.IHttpResponseListener;
import com.mahmud.test.webservice.RestClient.RequestMethod;
import android.os.AsyncTask;
import android.util.Log;

public abstract class RatingUpdateSync extends AsyncTask<String, Integer, String> implements IHttpResponseListener
{
	private String MOVIE_ID="movie_id";
	private String MOVIE_RATING="movie_rating";



	@Override
	protected String doInBackground(String... urls) 
	{
		String Url = urls[0];
		String movieId=urls[1];		
		String rating = urls[2];	
	
		Log.v("Urls:",urls+"");
		RestClient restClientObj=new RestClient(Url,false);		
		restClientObj.AddParam(MOVIE_RATING, rating);		
		restClientObj.AddParam(MOVIE_ID, movieId);
		try {
			restClientObj.Execute(RequestMethod.POST);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			postErrorResponse();
		}
		String json=restClientObj.getResponse();
		
		
		// TODO Auto-generated method stub
		return json;
	}
	
	@Override
	protected void onPostExecute(String result) {
		if(result==null){
			postErrorResponse();
		}
		else{
			postHttpResponse(result);
		}
		
	
	}

}
