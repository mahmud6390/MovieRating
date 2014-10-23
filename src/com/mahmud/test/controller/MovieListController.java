package com.mahmud.test.controller;

import java.util.ArrayList;
import java.util.List;

import com.mahmud.test.Urls;
import com.mahmud.test.dto.MovieList;
import com.mahmud.test.view.MovieListView;
import com.mahmud.test.webservice.MovieListSync;


public class MovieListController 
{
	MovieListView movieListViewObj;	
	
	public MovieListController(MovieListView movieListViewObj)
	{
		this.movieListViewObj=movieListViewObj;
		
	}
	
	public void getMovieList()
	{
		
		sendHttpRequest(Urls.URL_VIEW_MOVIE);
		
		
	}
	private void sendHttpRequest(String... urls)
	{
		MovieListSync httpRequest=new MovieListSync() {
			
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
		httpRequest.execute(urls);
		
	}
	
	
}
