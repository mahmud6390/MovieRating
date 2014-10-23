package com.mahmud.test.dto;

import java.util.List;

import com.mahmud.test.model.MoviePictureModel;


public class MoviePictureList {
	private List<MoviePictureModel> values;
	public List<MoviePictureModel> getContact(){
		return values;
	}
	public void setContactList(List<MoviePictureModel> values){
		this.values=values;
		
	}

}
