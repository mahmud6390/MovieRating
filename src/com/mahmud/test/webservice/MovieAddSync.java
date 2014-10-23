package com.mahmud.test.webservice;

import java.io.File;

import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;

import android.os.AsyncTask;
import android.util.Log;

import com.mahmud.test.IHttpResponseListener;
import com.mahmud.test.webservice.RestClient.RequestMethod;

public abstract class MovieAddSync extends AsyncTask<String, Integer, String> implements IHttpResponseListener
{
	private String MOVIE_ID="movie_id";
	private String MOVIE_NAME="movie_name";
	private String MOVIE_DESCRIPTION="movie_description";
	private String MOVIE_RATING="movie_rating";
	private String MOVIE_CATEGORY="movie_category";
	private String MOVIE_PATH="image_path";


	@Override
	protected String doInBackground(String... urls) 
	{
		String Url = urls[0];
		String name=urls[1];		
		String description = urls[2];
		String rating = urls[3];;
		String category = urls[4];
		String imagePath = urls[5];
	
		
		Log.v("URL>>>>>", Url+":"+imagePath);
		
		File file = new File(imagePath);
		FileBody fileBody = new FileBody(file);
		MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
		try{
		reqEntity.addPart("image", fileBody);
		reqEntity.addPart(MOVIE_NAME, new StringBody(name));
		reqEntity.addPart(MOVIE_DESCRIPTION, new StringBody(description));
		reqEntity.addPart(MOVIE_RATING, new StringBody(rating));
		reqEntity.addPart(MOVIE_CATEGORY, new StringBody(category));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		RestClient restClientObj=new RestClient(Url,true);
		restClientObj.AddMultiPart(reqEntity);
		
		
		
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
