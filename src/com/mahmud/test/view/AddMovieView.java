package com.mahmud.test.view;

import android.app.Activity;
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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.mahmud.test.R;
import com.mahmud.test.controller.MovieAddController;
import com.mahmud.test.model.MovieModel;

public class AddMovieView extends Activity {

	private static final int TAKE_GALLERY = 0;
	MovieModel movieObj;
	String  movieCategory, imagePath;	
	EditText nameTxt, descriptionTxt;
	RatingBar ratingBar;
	Spinner movieCategorySpn;
	ImageView movieImage;
	Button backBtn, saveBtn, cancelBtn, browseBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_movie);
		
		intUi();

		ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(this,
				R.array.movie_category, android.R.layout.simple_spinner_item);

		movieCategorySpn.setAdapter(list);

	}

	public void updateView(String response) 
	{
		Log.v("Response from view class", response);
		if (response != null) {
			Toast.makeText(this, response, 5).show();
			response=response.trim();
			if (response.equals("true")) {
				Toast.makeText(this, "Insert successfully", 5).show();
				finish();
			}

		} else {
			Toast.makeText(this, "Insert fail", 5).show();
		}

	}

	private void intUi() {
		movieImage = (ImageView) findViewById(R.id.imageView1);
		nameTxt = (EditText) findViewById(R.id.movieNameEdit);
		descriptionTxt = (EditText) findViewById(R.id.descriptionEdit);
		ratingBar = (RatingBar) findViewById(R.id.ratingBar1);
		movieCategorySpn = (Spinner) findViewById(R.id.spinner1);
		backBtn = (Button) findViewById(R.id.backBtn);
		saveBtn = (Button) findViewById(R.id.saveBtn);
		cancelBtn = (Button) findViewById(R.id.cancelBtn);
		browseBtn = (Button) findViewById(R.id.browse);

		ButtonClickListener onButtonClick = new ButtonClickListener();
		SpinnerItemSelectListener onSpinnerSelect = new SpinnerItemSelectListener();
		saveBtn.setOnClickListener(onButtonClick);
		cancelBtn.setOnClickListener(onButtonClick);
		backBtn.setOnClickListener(onButtonClick);
		browseBtn.setOnClickListener(onButtonClick);

		movieCategorySpn.setOnItemSelectedListener(onSpinnerSelect);

	}

	private class ButtonClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			int id = v.getId();
			switch (id) {
			case R.id.browse:

				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(i, TAKE_GALLERY);

				/*
				 * Intent intent = new Intent(Intent.ACTION_PICK);
				 * intent.setType("image/*"); startActivityForResult(intent,
				 * TAKE_GALLERY);
				 */
				break;
			case R.id.saveBtn:

				if (isEmptyMandatoryFeild()) {
					Toast.makeText(AddMovieView.this,
							"Please fill up mandatory feild", 5).show();
					return;
				}

				setMovieData();
				MovieAddController movieController = new MovieAddController(
						AddMovieView.this, movieObj);
				movieController.addMovie();

				break;
			case R.id.cancelBtn:
				finish();
				break;
			case R.id.backBtn:
				finish();
				break;
			default:
				break;
			}

		}

	}

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
				movieCategory = adapterView.getItemAtPosition(position)
						.toString();
				Log.v("movieCategory", "movieCategory:" + movieCategory);
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

	private void setMovieData() {
		movieObj = new MovieModel();
		movieObj.setMovie_name(nameTxt.getText().toString());
		movieObj.setMovie_description(descriptionTxt.getText().toString());
		movieObj.setMovie_category(movieCategory);
		movieObj.setMovie_rating(ratingBar.getRating());
		movieObj.setImage_path(imagePath);
		Log.v("Movie data", nameTxt.getText().toString() + ":"
				+ descriptionTxt.getText().toString() + ":" + movieCategory
				+ ":" + ratingBar.getRating() + ":" + imagePath);

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
