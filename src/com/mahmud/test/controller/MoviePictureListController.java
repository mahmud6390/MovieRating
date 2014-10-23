package com.mahmud.test.controller;

import java.util.ArrayList;
import java.util.List;

import android.net.Uri;
import android.util.Log;

import com.mahmud.test.Urls;
import com.mahmud.test.dto.MovieList;
import com.mahmud.test.dto.MoviePictureList;
import com.mahmud.test.view.MovieDetailsView;
import com.mahmud.test.webservice.MoviePictureListSync;


public class MoviePictureListController 
{
	MovieDetailsView movieListViewObj;	
	List<MoviePictureList> moviePicList=new ArrayList<MoviePictureList>();
	
	
	public MoviePictureListController(MovieDetailsView movieListViewObj)
	{
		this.movieListViewObj=movieListViewObj;
		
	}
	
	public void getMoviePictureList(String movieId)
	{
		
		
		
			Uri.Builder uriBuilder = Uri.parse(Urls.URL_MOVIE_PIC).buildUpon();
			uriBuilder.appendQueryParameter("movie_id", movieId);
			
			String urlString = null;
			try {
				urlString = uriBuilder.build().toString();
				sendHttpRequest(urlString);
				Log.v("URL", urlString+"");
			} catch (UnsupportedOperationException e) {
				e.printStackTrace();

			}
			
		
		
		
	}
	private void sendHttpRequest(String url)
	{
		MoviePictureListSync httpRequest=new MoviePictureListSync() {
			
			@Override
			public void postHttpResponse(String responseData) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void postHttpResponse(List responseData) {
				movieListViewObj.updateView(responseData);
				
			}
			
			@Override
			public void postErrorResponse() {
				movieListViewObj.updateView(null);
				
			}
		};
		httpRequest.execute(url);
		
	}
	
	
}
