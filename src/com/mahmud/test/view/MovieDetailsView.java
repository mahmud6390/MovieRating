package com.mahmud.test.view;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import com.mahmud.test.R;
import com.mahmud.test.DrawableManager;
import com.mahmud.test.controller.MoviePictureListController;
import com.mahmud.test.controller.MovieUpdateController;
import com.mahmud.test.model.MovieModel;
import com.mahmud.test.model.MoviePictureModel;

public class MovieDetailsView extends Activity {
	protected static final int TAKE_GALLERY = 0;
	EditText nameTxt, descriptionTxt;
	Spinner categorySpn;
	RatingBar ratingBar;
	ImageView movieImage;
	DrawableManager dm;
	Context context;
	List<MoviePictureModel> pictureList;
	String id, name, description, category, ratingId;
	float rating;
	String imagePath, userType;
	MovieModel movieObj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.movie_details);
		initUI();
		id = getIntent().getStringExtra(MovieListView.MOVIE_ID);
		name = getIntent().getStringExtra(MovieListView.MOVIE_NAME);
		description = getIntent().getStringExtra(MovieListView.MOVIE_DES);
		category = getIntent().getStringExtra(MovieListView.MOVIE_CAT);
		rating = getIntent().getFloatExtra(MovieListView.MOVIE_RATING, 0F);
		ratingId = getIntent().getStringExtra(MovieListView.RATING_ID);
		ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(this,
				R.array.movie_category, android.R.layout.simple_spinner_item);
		int position = list.getPosition(category);
		Log.v("Position", position + "");
		categorySpn.setSelection(position);
		categorySpn.setAdapter(list);

		MoviePictureListController pictureController = new MoviePictureListController(
				this);
		pictureController.getMoviePictureList(id);

	}

	public void updateView(List<MoviePictureModel> moviePicList) {

		nameTxt.setText(name);
		Log.v("category", category);

		descriptionTxt.setText(description);
		ratingBar.setRating(rating);
		if (moviePicList == null) {
			return;
		}
		this.pictureList = moviePicList;
		System.out.println(pictureList.size() + ":"
				+ moviePicList.get(0).getCount());
		if (moviePicList.get(0).getCount() > 0) {
			String originalPath = pictureList.get(0).getOriginalPath()
					.toString();
			Log.v("original pathin details view", originalPath + "");
			// image update
			dm = new DrawableManager();
			dm.fetchDrawableOnThread(originalPath, movieImage);
		} else {

		}

	}

	public void updateViewAfterDataUpdate(String response) {
		Log.v("Response from view class", response);
		if (response != null) {
			Toast.makeText(this, response, 5).show();
			response = response.trim();
			if (response.equals("true")) {
				Toast.makeText(this, "Insert successfully", 5).show();
				finish();
			}

		} else {
			Toast.makeText(this, "Insert fail", 5).show();
		}

	}

	private void initUI() {
		movieImage = (ImageView) findViewById(R.id.imageView);
		nameTxt = (EditText) findViewById(R.id.nameTextView);
		categorySpn = (Spinner) findViewById(R.id.categoryTextView);
		descriptionTxt = (EditText) findViewById(R.id.descriptionTextView);
		Button morePicBtn = (Button) findViewById(R.id.morePicButton);
		Button backBtn = (Button) findViewById(R.id.backBtn);
		Button updateBtn = (Button) findViewById(R.id.updateBtn);
		Button browseBtn = (Button) findViewById(R.id.browseBtn);
		ratingBar = (RatingBar) findViewById(R.id.ratingBar1);

		morePicBtn.setOnClickListener(onClickButtonEvent);
		backBtn.setOnClickListener(onClickButtonEvent);
		updateBtn.setOnClickListener(onClickButtonEvent);
		browseBtn.setOnClickListener(onClickButtonEvent);

		SpinnerItemSelectListener onSpinnerSelect = new SpinnerItemSelectListener();
		categorySpn.setOnItemSelectedListener(onSpinnerSelect);

		SharedPreferences spf = PreferenceManager
				.getDefaultSharedPreferences(this);
		userType = spf.getString("USERTYPE", "normal");
		if (userType.equalsIgnoreCase("admin")) {
			nameTxt.setEnabled(true);
			categorySpn.setClickable(true);
			descriptionTxt.setEnabled(true);
			browseBtn.setEnabled(true);
		}

	}

	View.OnClickListener onClickButtonEvent = new OnClickListener() {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.updateBtn:
				if (isEmptyMandatoryFeild()) {
					Toast.makeText(MovieDetailsView.this,
							"Please fill upmendatory feild", 5).show();
					return;
				}
				updateMovieData();
				if (userType.equals("admin")) {
					MovieUpdateController controller = new MovieUpdateController(
							MovieDetailsView.this, movieObj);
					controller.updateMovie();

				} else if (userType.equals("normal")) {
					rating = ratingBar.getRating();
					MovieUpdateController controller = new MovieUpdateController(
							MovieDetailsView.this, movieObj);
					controller.updateRating(id, rating);
				}

				break;
			case R.id.browseBtn:

				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(i, TAKE_GALLERY);
				break;

			case R.id.morePicButton:
				Toast.makeText(MovieDetailsView.this,
						"More picture will be shown", Toast.LENGTH_LONG).show();

				break;
			case R.id.backBtn:
				finish();
				break;
			default:
				break;

			}

		}

	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case TAKE_GALLERY:
			Uri selectedImageUri = data.getData();
			imagePath = getRealPathFromURI(selectedImageUri);
			Log.v("Image path:", imagePath);
			movieImage.setImageBitmap(BitmapFactory.decodeFile(imagePath));
			break;

		default:
			break;
		}
	}

	private class SpinnerItemSelectListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> adapterView, View v,
				int position, long arg3) {
			int id = adapterView.getId();
			Log.v("movieCategory", id + ":" + R.id.spinner1);
			switch (id) {
			case R.id.spinner1:
				category = adapterView.getItemAtPosition(position).toString();
				Log.v("movieCategory", "movieCategory:" + category);
				break;

			default:
				break;
			}

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}

	}

	private boolean isEmptyMandatoryFeild() {

		if ((nameTxt.getText().length() == 0))
			return true;
		else
			return false;

	}

	private void updateMovieData() {
		movieObj = new MovieModel();
		movieObj.setMovie_name(nameTxt.getText().toString());
		movieObj.setMovie_description(descriptionTxt.getText().toString());
		movieObj.setMovie_category(category);
		movieObj.setMovie_rating(ratingBar.getRating());
		movieObj.setMovie_id(id);
		movieObj.setRating_id(ratingId);
		movieObj.setImage_path(imagePath);
		Log.v("Movie data", nameTxt.getText().toString() + ":"
				+ descriptionTxt.getText().toString() + ":" + category + ":"
				+ ratingBar.getRating() + ":" + imagePath);

	}

	public String getRealPathFromURI(Uri contentUri) {

		Cursor cursor = getContentResolver().query(contentUri, null, null,
				null, null);
		if (cursor == null) { // Source is Dropbox or other similar local file
								// path
			return contentUri.getPath();
		} else {
			cursor.moveToFirst();
			int idx = cursor
					.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
			return cursor.getString(idx);
		}

	}

}
