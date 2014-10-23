package com.mahmud.test;

import java.util.ArrayList;
import java.util.List;

import com.mahmud.test.model.MovieModel;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
/*
 * This classing using for custom grid view genaration.It received the movie list and set in gridview.
 */
public class GridViewCustomAdapter extends BaseAdapter {
	Context con;


	private LayoutInflater mInflater;
	boolean[] checkedFlag;
	DrawableManager dm;
	List<MovieModel> movieList=new ArrayList<MovieModel>();

	public GridViewCustomAdapter(List<MovieModel> movieList,Context con) {
		this.movieList = movieList;
		checkedFlag = new boolean[movieList.size()];
		dm=new DrawableManager();
		this.con = con;
		mInflater = LayoutInflater.from(con);

		// Collections.sort(displayNameArrayList); // Must be sorted!
		// TODO Auto-generated constructor stub

	}
	ViewHolder holder;
	
	public View getView(final int position, View convertView, ViewGroup parent) {
		

		if (convertView == null) {
			// rowView = inflater.inflate(R.layout.main, parent, false);
			convertView = mInflater.inflate(R.layout.movie_list, null);
			holder = new ViewHolder();
			holder.appImage = (ImageView) convertView
					.findViewById(R.id.imageView1);
			
			holder.movieName=(TextView)convertView
					.findViewById(R.id.appName);
			holder.ratingBar=(RatingBar)convertView
					.findViewById(R.id.ratingBar1);
			//important otherwise NPE
			holder.appImage.setTag(holder);
			
			//
			convertView.setTag(holder);

		}

		else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		MovieModel movieObj=movieList.get(position);
		String movieName=movieObj.getMovie_name();
		String movieId=movieObj.getMovie_id();
		float rating=movieObj.getMovie_rating();
		String imagePath=movieObj.getImage_path();
		Log.v("Movie List",movieId+":"+movieName+":"+rating+":"+imagePath);
		holder.movieName.setText(movieName);
		holder.ratingBar.setRating(rating);
		
		if(imagePath!=null)
		{

			dm.fetchDrawableOnThread(imagePath,holder.appImage);
			
		}
		

		return convertView;
	}

	static class ViewHolder {
		ImageView appImage;
		TextView movieName;
		RatingBar ratingBar;

	

		// Button callBtn;

	}

	public int getCount() {
		// TODO Auto-generated method stub
		return movieList.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}


}
