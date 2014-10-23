package com.mahmud.test.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.mahmud.test.R;
import com.mahmud.test.Utilites;
import com.mahmud.test.controller.LoginController;
import com.mahmud.test.dto.LoginList;
import com.mahmud.test.model.LoginModel;

public class LoginView extends Activity {
	EditText emailTxt, passwordTxt;
	LoginModel userInfo;
	LoginController loginControllerObj;
	SharedPreferences pref;
	Context con;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_view);
		con=this;
		initUI();

	}

	
	public void updateView(LoginList loginResponseObj)
	{
		if(loginResponseObj==null){
			Toast.makeText(this, "Login failed.Try again!!", Toast.LENGTH_LONG).show();
			return;
		}
		String status=loginResponseObj.getLogin().get(0).getStatus();
		Log.v("Status in login view",status+"");
		if(status.equals("true"))
		{
			String userId=loginResponseObj.getLogin().get(0).getUser_id();
			String userAccessType=loginResponseObj.getLogin().get(0).getAccess_type();
			pref=PreferenceManager.getDefaultSharedPreferences(this);
			Editor editor=pref.edit();
			editor.putInt("FLAG", 1);
			editor.putString("USERID", userId);
			editor.putString("USERTYPE", userAccessType);
			editor.commit();
			Toast.makeText(this, "Login successfully", Toast.LENGTH_LONG).show();
			finish();
			startActivity(new Intent(this,MovieListView.class));
		}
		else{
			Toast.makeText(this, "Login failed.Try again!!", Toast.LENGTH_LONG).show();
		}
	
		
	}
	private void initUI()
	{
		emailTxt = (EditText) findViewById(R.id.userNameEditText);
		passwordTxt = (EditText) findViewById(R.id.passwordEditText);
		Button loginBtn = (Button) findViewById(R.id.loginBtn);
		ButtonClickListener buttonClick=new ButtonClickListener();
		loginBtn.setOnClickListener(buttonClick);
	}
	private boolean isEmptyMandatoryFeild()
	{
		
		if((emailTxt.getText().length()==0) || (passwordTxt.getText().length()==0)) return true;
		else return false;
		
	}
	private class ButtonClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {

			case R.id.loginBtn:
				if(isEmptyMandatoryFeild()){
					Toast.makeText(con, "Please fill up email and password", 5).show();
					return;
				}
				userInfo = new LoginModel();				
				userInfo.setEmail(emailTxt.getText().toString());
				userInfo.setPassword(passwordTxt.getText().toString());
				if(Utilites.checkConn(LoginView.this))
				{
				loginControllerObj=new LoginController(userInfo, LoginView.this);
				loginControllerObj.checkLoginInfo();
				}
				else
				{
					//offline browse
					/*LoginCacheController cacheController=new LoginCacheController(userInfo, LoginView.this, con);
					cacheController.fetchLogin();*/
				}
				
				break;
			default:
				break;

			}
			
		}
		
	}


	

}
