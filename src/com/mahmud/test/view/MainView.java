package com.mahmud.test.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class MainView extends Activity
{
	SharedPreferences pref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		pref=PreferenceManager.getDefaultSharedPreferences(this);
		//this flag using if same user login again.
		int flag=pref.getInt("FLAG", 0);
		if(flag==0)
		{
			finish();
			startActivity(new Intent(this,LoginView.class));
		}
		else
		{
			finish();
			startActivity(new Intent(this,MovieListView.class));
		}
		
	}

}
