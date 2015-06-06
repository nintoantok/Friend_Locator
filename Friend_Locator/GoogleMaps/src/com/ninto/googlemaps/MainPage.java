package com.ninto.googlemaps;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.parse.ParseUser;

import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class MainPage extends TabActivity 
{

	LocationManager locmngr;
	Location our_location;
	Double L1 = 10.000886500000000000;  // setting an initial latitude.
	Double L2 = 76.299572799999960000;
	LocationListener loclis;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainpage);

		Resources re=getResources();
		TabHost th=getTabHost();
		TabHost.TabSpec spec;
		Intent i;

		
		
		locmngr=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
		loclis =new MyLocationListner();
		our_location=locmngr.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		locmngr.requestLocationUpdates( LocationManager.NETWORK_PROVIDER, 0, 0,loclis);
		
		
		

		i=new Intent(this,Profile.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		spec = th.newTabSpec("tab1").setIndicator("",re.getDrawable(R.drawable.users_newone)).setContent(i);
		th.addTab(spec);

		i=new Intent(this,Friends_page.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		spec = th.newTabSpec("tab2").setIndicator("",re.getDrawable(R.drawable.friend_list_new)).setContent(i);
		th.addTab(spec);
		
		i=new Intent(this,Notification_new.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		spec = th.newTabSpec("tab3").setIndicator("",re.getDrawable(R.drawable.notification	)).setContent(i);
		th.addTab(spec);

		th.setCurrentTab(0);

	}




	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_menu, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case R.id.logout_menu:
		{
			ParseUser.logOut();
			Toast.makeText(this, "Please Log In, to continue", Toast.LENGTH_SHORT).show();
			Intent i =new Intent(getApplicationContext(),Login_page.class);
			startActivity(i);
			MainPage.this.finish();
			break;
		}

		case R.id.history_menu:
			Intent i = new Intent(this,G_Maps.class);
			startActivity(i);
			break;

		}
		return true;
	}
	
	
	public class MyLocationListner implements LocationListener{

		@Override
		public void onLocationChanged(Location location)
		{
			// TODO Auto-generated method stub
			L1=location.getLatitude();
			L2=location.getLongitude();
			String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
			//			Toast.makeText(getApplicationContext(), currentDateTimeString, Toast.LENGTH_SHORT).show();
			DatabaseHandlerActivity db = new DatabaseHandlerActivity(getApplicationContext());
			db.addLocation(new Locations_viewer(L1.toString(), L2.toString(), currentDateTimeString));

						Toast.makeText(getApplicationContext(), "New Location   "+"Latitude ="+L1+
								"   Longitude ="+L2, Toast.LENGTH_LONG).show();

			
			Calendar c = Calendar.getInstance();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String formattedDate = df.format(c.getTime());
			
			
			ParseUser.getCurrentUser().put("Latitude", L1.toString());
			ParseUser.getCurrentUser().put("Longitude", L2.toString());
			
			ParseUser.getCurrentUser().put("Time", formattedDate);
			
			
			ParseUser.getCurrentUser().saveInBackground();

			//			Toast.makeText(getApplicationContext(), "Latitude ="+L1, Toast.LENGTH_LONG).show();
			//			Toast.makeText(getApplicationContext(), "Longitude ="+L2, Toast.LENGTH_LONG).show();



		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}
	}
	

}
