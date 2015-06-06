//package com.ninto.googlemaps;
//
//import java.text.DateFormat;
//import java.util.Date;
//
//import com.parse.ParseUser;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Toast;
//
//public class LocmngrActivity extends Activity
//{
//	LocationManager locmngr;
//	Location our_location;
//	Double L1 = 10.000886500000000000;
//	Double L2 = 76.299572799999960000;
//	LocationListener loclis;
//	/** Called when the activity is first created. */
//	@Override
//	public void onCreate(Bundle savedInstanceState)
//	{
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.main);
//		locmngr=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
//		loclis =new MyLocationListner();
//		our_location=locmngr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//		locmngr.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0,loclis);
//		
//
//
//	}
//	public void button(View v)
//	{
//
//
//		Toast.makeText(getApplicationContext(), "Latitude ="+L1, Toast.LENGTH_LONG).show();
//		Toast.makeText(getApplicationContext(), "Longitude ="+L2, Toast.LENGTH_LONG).show();
//
//	}
//	public void button2(View v)
//	{
//		Intent i =new Intent(getApplicationContext(),G_Maps.class);
//		i.putExtra("lat", L1.toString());
//		i.putExtra("long", L2.toString());
//		startActivity(i);
//	}
//	public class MyLocationListner implements LocationListener{
//
//		@Override
//		public void onLocationChanged(Location location)
//		{
//			// TODO Auto-generated method stub
//			L1=location.getLatitude();
//			L2=location.getLongitude();
//			String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
////			Toast.makeText(getApplicationContext(), currentDateTimeString, Toast.LENGTH_SHORT).show();
////			DatabaseHandlerActivity db = new DatabaseHandlerActivity(getApplicationContext());
////			db.addLocation(new Locations_viewer(L1.toString(), L2.toString(), currentDateTimeString));
//
////			Toast.makeText(getApplicationContext(), "New Location   "+"Latitude ="+L1+
////					"   Longitude ="+L2, Toast.LENGTH_LONG).show();
////			
////			ParseUser.getCurrentUser().put("Latitude", L1.toString());
////			ParseUser.getCurrentUser().saveInBackground();
//			
////			Toast.makeText(getApplicationContext(), "Latitude ="+L1, Toast.LENGTH_LONG).show();
////			Toast.makeText(getApplicationContext(), "Longitude ="+L2, Toast.LENGTH_LONG).show();
//			
//			
//			
//		}
//
//		@Override
//		public void onProviderDisabled(String provider) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void onProviderEnabled(String provider) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void onStatusChanged(String provider, int status, Bundle extras) {
//			// TODO Auto-generated method stub
//
//		}
//	}
//
//}