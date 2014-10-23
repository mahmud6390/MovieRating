package com.mahmud.test.controller;

import java.util.ArrayList;
import java.util.List;

import com.mahmud.test.Urls;
import com.mahmud.test.dto.MovieList;
import com.mahmud.test.model.MovieModel;
import com.mahmud.test.view.AddMovieView;
import com.mahmud.test.webservice.MovieAddSync;


public class MovieAddController 
{
	AddMovieView movieViewObj;
	MovieModel movieObj;
	List<MovieList> movieList=new ArrayList<MovieList>();
	
	
	public MovieAddController(AddMovieView movieViewObj,MovieModel movieObj)
	{
		this.movieViewObj=movieViewObj;
		this.movieObj=movieObj;
	}
	
	public void addMovie()
	{
		
		sendHttpRequest(Urls.URL_ADD_MOVIE,movieObj.getMovie_name(),movieObj.getMovie_description(),movieObj.getMovie_rating()+"",
				movieObj.getMovie_category(),movieObj.getImage_path());
		
		
	}
	private void sendHttpRequest(String... urls)
	{
		MovieAddSync httpRequest=new MovieAddSync() {
			
			@Override
			public void postHttpResponse(String responseData) {
				// TODO Auto-generated method stub
				movieViewObj.updateView(responseData);
			}
			
			@Override
			public void postHttpResponse(List responseData) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void postErrorResponse() {
				movieViewObj.updateView(null);
				
			}
		};
		httpRequest.execute(urls);
		
	}
	
	
}
