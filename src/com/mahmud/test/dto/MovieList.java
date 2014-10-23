package com.mahmud.test.dto;

import java.util.List;

import com.mahmud.test.model.MovieModel;


public class MovieList {
	private List<MovieModel> values;
	public List<MovieModel> getContact(){
		return values;
	}
	public void setContactList(List<MovieModel> values){
		this.values=values;
		
	}

}
