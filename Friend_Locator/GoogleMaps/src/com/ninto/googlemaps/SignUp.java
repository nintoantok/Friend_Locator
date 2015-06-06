package com.ninto.googlemaps;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends Activity
{
	EditText usernameField, passwordField, emailField;
	String username, password, email;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup_page);
		usernameField = (EditText)findViewById(R.id.username);
		passwordField = (EditText)findViewById(R.id.password);
		emailField = (EditText)findViewById(R.id.email);
	}
	public void sign_up(View v)
	{
		
		
		

		
//		TelephonyManager telemamanger = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//		String simSerialNumber = telemamanger.getSimSerialNumber();
//		String uniqueNumber = telemamanger.getDeviceId();
		
		

		if (usernameField.getText().length()>0) {
			if (passwordField.getText().length()>0) {
				if (emailField.getText().length()>0) {
					
				
				username = usernameField.getText().toString().trim();
				password = passwordField.getText().toString().trim();
				email = emailField.getText().toString().trim();
				
				
				ParseUser user = new ParseUser();
				user.setUsername(username);
				user.setPassword(password);
				user.setEmail(email);
				user.put("Latitude", "10.523067500000000000");
				user.put("Longitude", "76.222210599999920000");
				
				
				Calendar c = Calendar.getInstance();
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		        String formattedDate = df.format(c.getTime());
				
				
				user.put("Time", formattedDate);
				
//				user.put("SimSerialNumber", simSerialNumber);
//				user.put("UniqueNumber", uniqueNumber);
				
				final ProgressDialog mDialog = new ProgressDialog(
						SignUp.this);
				mDialog.setMessage("Signing Up..");

				mDialog.show();

				user.signUpInBackground(new SignUpCallback() {
					public void done(ParseException e) {

						mDialog.cancel();

						if (e == null) {
							Toast toast = Toast.makeText(
									getApplicationContext(),
									"Successful signUp! ",
									Toast.LENGTH_LONG);
							toast.show();
							Intent signupScreen = new Intent(
									getApplicationContext(),
									Login_page.class);
							startActivity(signupScreen);
							// Hooray! Let them use the app now.

						} else {

							AlertDialog.Builder builder = new AlertDialog.Builder(
									SignUp.this);
							builder.setMessage(e.getMessage())
									.setCancelable(false)

									.setNegativeButton(
											"Ok!",
											new DialogInterface.OnClickListener() {
												public void onClick(
														DialogInterface dialog,
														int id) {
													dialog.cancel();
												}
											});
							AlertDialog alert = builder.create();
							alert.show();

							// Sign up didn't succeed. Look at the
							// ParseException
							// to figure out what went wrong

						}
					}
				});
			} 
				else
				{
					Toast toast = Toast.makeText(getApplicationContext(),
							"Please enter an email! ", Toast.LENGTH_LONG);
					toast.show();
				}
				
			}
				else {
				Toast toast = Toast.makeText(getApplicationContext(),
						"Please enter a password! ", Toast.LENGTH_LONG);
				toast.show();

			}

		} else {
			Toast toast = Toast.makeText(getApplicationContext(),
					"Please enter a valid username! ",
					Toast.LENGTH_LONG);
			toast.show();
		}


	}
}
