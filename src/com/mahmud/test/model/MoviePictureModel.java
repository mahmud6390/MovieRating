package com.mahmud.test.model;

public class MoviePictureModel 
{
	int pictureId;	
	String originalPath;
	String localPath;
	int count;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getLocalPath() {
		return localPath;
	}
	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}
	public int getPictureId() {
		return pictureId;
	}
	public void setPictureId(int pictureId) {
		this.pictureId = pictureId;
	}
	
	public String getOriginalPath() {
		return originalPath;
	}
	public void setOriginalPath(String originalPath) {
		this.originalPath = originalPath;
	}
	
	

}
