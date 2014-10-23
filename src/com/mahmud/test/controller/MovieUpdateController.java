package com.mahmud.test.controller;

import java.util.ArrayList;
import java.util.List;

import com.mahmud.test.Urls;
import com.mahmud.test.dto.MovieList;
import com.mahmud.test.model.MovieModel;
import com.mahmud.test.view.AddMovieView;
import com.mahmud.test.view.MovieDetailsView;
import com.mahmud.test.webservice.MovieAddSync;
import com.mahmud.test.webservice.MovieUpdateSync;
import com.mahmud.test.webservice.RatingUpdateSync;


public class MovieUpdateController 
{
	MovieDetailsView movieViewObj;
	MovieModel movieObj;
	List<MovieList> movieList=new ArrayList<MovieList>();
	
	
	public MovieUpdateController(MovieDetailsView movieViewObj,MovieModel movieObj)
	{
		this.movieViewObj=movieViewObj;
		this.movieObj=movieObj;
	}
	
	public void updateMovie()
	{
		
		sendHttpRequest(Urls.URL_UPDATE_MOVIE,movieObj.getMovie_name(),movieObj.getMovie_description(),movieObj.getMovie_rating()+"",
				movieObj.getMovie_category(),movieObj.getImage_path(),movieObj.getMovie_id(),movieObj.getRating_id());
		
		
	}
	public void updateRating(String movieId,float rating){
		RatingUpdateSync httpResult=new RatingUpdateSync() {
			
			@Override
			public void postHttpResponse(String responseData) {
				// TODO Auto-generated method stub
				movieViewObj.updateViewAfterDataUpdate(responseData);
			}
			
			@Override
			public void postHttpResponse(List responseData) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void postErrorResponse() {
				movieViewObj.updateViewAfterDataUpdate(null);
				
			}
		};
		httpResult.execute(Urls.URL_UPDATE_MOVIE_RATING,movieId,rating+"");
	}
	private void sendHttpRequest(String... urls)
	{
		MovieUpdateSync httpRequest=new MovieUpdateSync() {
			
			@Override
			public void postHttpResponse(String responseData) {
				// TODO Auto-generated method stub
				movieViewObj.updateViewAfterDataUpdate(responseData);
			}
			
			@Override
			public void postHttpResponse(List responseData) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void postErrorResponse() {
				// TODO Auto-generated method stub
				
			}
		};
		httpRequest.execute(urls);
		
	}
	
	
}
