package com.ninto.googlemaps;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Login_page extends Activity
{

	EditText userField, passField;
	String username, password;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser != null) {
			// do stuff with the user
			Intent i=new Intent(getApplicationContext(),MainPage.class);
			startActivity(i);
		}
		else
		{
			setContentView(R.layout.login_page);
			userField = (EditText)findViewById(R.id.username);
			passField = (EditText)findViewById(R.id.password);
		}


	}


	
	public void login(View v)
	{



		username = userField.getText().toString().trim();
		password = passField.getText().toString().trim();

		final ProgressDialog mDialog = new ProgressDialog(this);
		mDialog.setMessage("Login Progress..");

		mDialog.show();

		ParseUser.logInInBackground(username, password,
				new LogInCallback() {

			@Override
			public void done(ParseUser user, ParseException e) {
				if (user != null) {
					// Hooray! The user is logged in.

					mDialog.cancel();
					Toast toast = Toast.makeText(
							getApplicationContext(),
							"Successfull Login! ",
							Toast.LENGTH_LONG);
					toast.show();

					Intent signupScreen = new Intent(getApplicationContext(),
							MainPage.class);
					startActivity(signupScreen);



				} else {

					mDialog.cancel();
					// Signup failed. Look at the ParseException
					// to see what happened. 

					AlertDialog.Builder builder = new AlertDialog.Builder(Login_page.this);
					builder.setMessage(e.getMessage())
					.setCancelable(false)

					.setNegativeButton("Ok!", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							dialog.cancel();
						}
					});
					AlertDialog alert = builder.create();
					alert.show();

				}

			}


		});



	}
	public void signup(View v)
	{
		Intent i = new Intent(getApplicationContext(),SignUp.class);
		startActivity(i);
	}
}
