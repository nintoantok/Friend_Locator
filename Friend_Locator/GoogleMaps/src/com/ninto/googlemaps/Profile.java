package com.ninto.googlemaps;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Profile extends Activity 
{

	Boolean req_status=false;
	Boolean noti_status=false;
	String username, email;
	TextView name, mail;
	EditText search;
	ListView SearchList;
	ArrayAdapter<String> Adapter;
	//	ArrayList<String> friends_Id = new ArrayList<String>();
	ProgressDialog myPd_ring;
//	LocationManager locmngr;
//	Location our_location;
//	Double L1 = 10.000886500000000000;  // setting an initial latitude.
//	Double L2 = 76.299572799999960000;
//	LocationListener loclis;
	volatile Boolean check_delay=true;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_page);

		search = (EditText)findViewById(R.id.search);
		SearchList=(ListView)findViewById(R.id.search_list);

		name = (TextView)findViewById(R.id.user);
		mail = (TextView)findViewById(R.id.email);

		username = ParseUser.getCurrentUser().getUsername();
		email = ParseUser.getCurrentUser().getEmail();



		name.setText(username);
		mail.setText(email);




//		locmngr=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
//		loclis =new MyLocationListner();
//		our_location=locmngr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//		locmngr.requestLocationUpdates( LocationManager.GPS_PROVIDER, 0, 0,loclis);


	}
	public void search_method(View v)
	{


		Adapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
		if(Adapter.isEmpty()==false)
			Adapter.clear();
		final String search_item=search.getText().toString().trim();



		myPd_ring=ProgressDialog.show(Profile.this, "Please wait", "Loading please wait..", true);
		myPd_ring.setCancelable(true);
		new Thread(new Runnable() {  
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try
				{
					ParseQuery match =ParseUser.getQuery();
					match.whereEqualTo("username", search_item);
					match.findInBackground(new FindCallback() {
						public void done(List<ParseObject> objects, ParseException e) {
							if (e == null) {
								// The query was successful.
								for(ParseObject i: objects){

									Adapter.add(i.getString("username"));
									//									friends_Id.add(i.getString("objectId"));

								}

								set_search_list();
							} else {
								// Something went wrong.
							}
						}


					}); 

					Thread.sleep(5000);
				}catch(Exception e){}

				//				myPd_ring.cancel();
			}
		}).start();


	}

	public void set_search_list()
	{




		if(Adapter.isEmpty()){

			myPd_ring.cancel();
			AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
			builder.setMessage("No Results Found")
			.setCancelable(false)

			.setNegativeButton("Ok!", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}
			});
			AlertDialog alert = builder.create();
			alert.show();

		}

		myPd_ring.cancel();
		Adapter.notifyDataSetChanged();


		SearchList.setAdapter(Adapter);

		SearchList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,  int position,
					long arg3) {
				// TODO Auto-generated method stub


				String our_name = ParseUser.getCurrentUser().getUsername();



				//Checking whether friend is already added
				Check_friend(position,our_name);

//				try {
//					Thread.sleep(10000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				Toast.makeText(getApplicationContext(), "friends status CHECKING=  "+req_status.toString(), Toast.LENGTH_LONG).show();
//				if(req_status==false)
//				{
//					AlertDialog.Builder build = new AlertDialog.Builder(Profile.this);
//					build.setMessage("You are already friends with "+Adapter.getItem(position)+"!")
//					.setCancelable(false)
//					.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//
//						@Override
//						public void onClick(DialogInterface dia, int which) {
//							// TODO Auto-generated method stub
//							dia.dismiss();
//						}
//					}).show();
//				}


				//checking whether the notification is in progress

				//else sending notification
				

			}		


		});
	}



		public Boolean Check_notification(int position,String our_name)
		{

			ParseQuery noti_1 = new ParseQuery("Request");
			noti_1.whereEqualTo("UserId", Adapter.getItem(position));
			noti_1.whereEqualTo("RequstingId", our_name);


			ParseQuery noti_2 = new ParseQuery("Request");
			noti_2.whereEqualTo("UserId", our_name);
			noti_2.whereEqualTo("RequstingId", Adapter.getItem(position));

			List<ParseQuery> queries = new ArrayList<ParseQuery>();
			queries.add(noti_1);
			queries.add(noti_2);

			ParseQuery mainQuery = ParseQuery.or(queries);
			mainQuery.findInBackground(new FindCallback() {
				public void done(List<ParseObject> results, ParseException e) {



					noti_status=results.isEmpty();//true if empty


				}

			});


			return noti_status;
		}


		public void Check_friend(final int position, final String our_name)
		{
			final ProgressDialog obj_dlg=new ProgressDialog(Profile.this);
			obj_dlg.setMessage("Please wait");
			obj_dlg.setCancelable(true);
			obj_dlg.show();
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					ParseQuery friend_query = new ParseQuery("Friends");

					friend_query.whereEqualTo("UserId", our_name);
					friend_query.whereEqualTo("FriendId", Adapter.getItem(position));
					friend_query.findInBackground(new FindCallback() {

						@Override
						public void done(List<ParseObject> friends, ParseException arg1) {
							// TODO Auto-generated method stub


							req_status=friends.isEmpty();  //true if empty
//							Toast.makeText(getApplicationContext(), "friends status=  "+req_status.toString(), Toast.LENGTH_LONG).show();

							
							
							if(req_status==false)
							{
								obj_dlg.cancel();
								AlertDialog.Builder build = new AlertDialog.Builder(Profile.this);
								build.setMessage("You are already friends with "+Adapter.getItem(position)+"!")
								.setCancelable(false)
								.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			
									@Override
									public void onClick(DialogInterface dia, int which) {
										// TODO Auto-generated method stub
										dia.dismiss();
									}
								}).show();
							}
							
							else{
								obj_dlg.cancel();
								send_request(position);
							}
							
					

						}
						
						
						
						
						
						
					});


				}
			}).start();




		}


		public void send_request(final int position)
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
			builder.setMessage("Do you want to add "+Adapter.getItem(position)+" as your friend?")
			.setCancelable(false)

			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					String our_Name = ParseUser.getCurrentUser().getUsername();			
					ParseObject Request = new ParseObject("Request");


					Request.put("UserId",Adapter.getItem(position));
					Request.put("RequestingId", our_Name);
					Request.saveInBackground();
					Toast.makeText(getApplicationContext(), "Request sent", Toast.LENGTH_SHORT).show();


				}
			})
			.setNegativeButton("No", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.cancel();
				}

			});
			AlertDialog alert = builder.create();
			alert.show();
		}


		@Override
		public void onBackPressed() {
			// TODO Auto-generated method stub
			//		super.onBackPressed();
			new AlertDialog.Builder(Profile.this)
			.setTitle("Are you sure,")
			.setMessage("Want to exit?")
			.setNegativeButton("No", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.dismiss();
				}
			})
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog_main, int which) {
					// TODO Auto-generated method stub
					dialog_main.dismiss();
					finish();
				}
			}).show();
		}



//		public class MyLocationListner implements LocationListener{
//
//			@Override
//			public void onLocationChanged(Location location)
//			{
//				// TODO Auto-generated method stub
//				L1=location.getLatitude();
//				L2=location.getLongitude();
//				String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
//				//			Toast.makeText(getApplicationContext(), currentDateTimeString, Toast.LENGTH_SHORT).show();
//				DatabaseHandlerActivity db = new DatabaseHandlerActivity(getApplicationContext());
//				db.addLocation(new Locations_viewer(L1.toString(), L2.toString(), currentDateTimeString));
//
//				//			Toast.makeText(getApplicationContext(), "New Location   "+"Latitude ="+L1+
//				//					"   Longitude ="+L2, Toast.LENGTH_LONG).show();
//
//				ParseUser.getCurrentUser().put("Latitude", L1.toString());
//				ParseUser.getCurrentUser().put("Longitude", L2.toString());
//				ParseUser.getCurrentUser().saveInBackground();
//
//				//			Toast.makeText(getApplicationContext(), "Latitude ="+L1, Toast.LENGTH_LONG).show();
//				//			Toast.makeText(getApplicationContext(), "Longitude ="+L2, Toast.LENGTH_LONG).show();
//
//
//
//			}
//
//			@Override
//			public void onProviderDisabled(String provider) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onProviderEnabled(String provider) {
//				// TODO Auto-generated method stub
//
//			}
//
//			@Override
//			public void onStatusChanged(String provider, int status, Bundle extras) {
//				// TODO Auto-generated method stub
//
//			}
//		}
	}
