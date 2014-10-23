package com.mahmud.test.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import com.mahmud.test.R;
import com.mahmud.test.GridViewCustomAdapter;
import com.mahmud.test.controller.MovieListController;
import com.mahmud.test.model.MovieModel;

public class MovieListView extends Activity {
	List<MovieModel> movieList;
	GridView gridView;
	public static String MOVIE_ID="movie_id";
	public static String MOVIE_NAME="movie_name";
	public static String MOVIE_DES="movie_des";
	public static String MOVIE_CAT="movie_cat";
	public static String MOVIE_RATING="movie_rat";
	public static String RATING_ID="rating_id";
	TextView noDataText;
	Context con;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movie_list_view);
		con=this;
		initUI();
		getMovieList();
	}
	private void getMovieList()
	{
		movieList=new ArrayList<MovieModel>();
		MovieListController listControllerObj=new MovieListController(this);
		listControllerObj.getMovieList();
		
	}
	private Handler searchHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			

			switch (msg.what) {
			case 1:
				 
	       		GridViewCustomAdapter adapter=new GridViewCustomAdapter(movieList, con);
	       		gridView.setAdapter(adapter);
	               
				break;
			}
		}
    };

	public void updateView(List<MovieModel> movieList) {
		if(movieList==null){
			noDataText.setVisibility(View.VISIBLE);
			noDataText.setText("No data found");
			return;
		}
		this.movieList = movieList;
		String status=movieList.get(0).getStatus();
		Log.v("Status in movie view",status+"");
		
		if(status.equals("false")){
			noDataText.setVisibility(View.VISIBLE);
			noDataText.setText("No data found");
		}
		else{
			noDataText.setVisibility(View.GONE);
			Message msg = new Message();
			msg.what = 1;
			searchHandler.sendMessageDelayed(msg, 1);
		}
		

	}
	private void initUI(){
		Button addBtn=(Button)findViewById(R.id.addMovieBtn);
		Button logoutBtn=(Button)findViewById(R.id.logoutBtn);
		Button refreshBtn=(Button)findViewById(R.id.refreshBtn);
		noDataText=(TextView)findViewById(R.id.textView1);
		
		gridView=(GridView)findViewById(R.id.gridView1);
		ButtonClickListener  buttonClickListnerObj=new ButtonClickListener();
		GridViewItemClickListener gridItemClick=new GridViewItemClickListener();
		
		addBtn.setOnClickListener(buttonClickListnerObj);
		logoutBtn.setOnClickListener(buttonClickListnerObj);
		refreshBtn.setOnClickListener(buttonClickListnerObj);
		gridView.setOnItemClickListener(gridItemClick);
		SharedPreferences spf=PreferenceManager.getDefaultSharedPreferences(this);
		if(spf.getString("USERTYPE", "normal").equalsIgnoreCase("admin")){
			
			addBtn.setVisibility(View.VISIBLE);
		}
		
	}
	private class ButtonClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.addMovieBtn:
				startActivity(new Intent(MovieListView.this,AddMovieView.class));
				break;
			case R.id.logoutBtn:
				SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(MovieListView.this);
				Editor editor=pref.edit();
				editor.putInt("FLAG", 0);
				editor.putString("USERID", "0");
				editor.putString("USERTYPE", "normal");
				editor.commit();
				finish();
				Intent i=new Intent(MovieListView.this,LoginView.class);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
				break;
			case R.id.refreshBtn:
				gridView.invalidate();
				getMovieList();
				break;	

			default:
				break;
			}
			
		}
		
	}
	private class GridViewItemClickListener implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> v, View arg1, int position,
				long arg3) {
			switch (v.getId()) {
			case R.id.gridView1:
				String movieId= movieList.get(position).getMovie_id();
				String movieName=movieList.get(position).getMovie_name();
				String movieDes=movieList.get(position).getMovie_description();
				String movieCat=movieList.get(position).getMovie_category();
				float rating=movieList.get(position).getMovie_rating();
				String ratingId=movieList.get(position).getRating_id();
				Intent detailsIntent=new Intent(MovieListView.this,MovieDetailsView.class);
				detailsIntent.putExtra(MOVIE_ID, movieId);
				detailsIntent.putExtra(MOVIE_NAME, movieName);
				detailsIntent.putExtra(MOVIE_DES, movieDes);
				detailsIntent.putExtra(MOVIE_CAT, movieCat);
				detailsIntent.putExtra(MOVIE_RATING,rating);
				detailsIntent.putExtra(RATING_ID,ratingId);
				startActivity(detailsIntent);
				break;

			default:
				break;
			}
			
		}
		
	}

}
