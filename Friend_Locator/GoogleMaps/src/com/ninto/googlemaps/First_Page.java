package com.ninto.googlemaps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class First_Page extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first_page);
	}
	public void okay(View v)
	{
		Intent i = new Intent(getApplicationContext(),Login_page.class);
		startActivity(i);
	}
}
