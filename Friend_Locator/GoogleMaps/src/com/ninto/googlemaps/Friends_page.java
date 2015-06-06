package com.ninto.googlemaps;

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
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Friends_page extends Activity
{

	ProgressDialog myPd_ring;
	ListView friends_list;
	List<ParseObject> loc_friend;
	String lat, lon;
	String  Time;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.friends_page);
		friends_list = (ListView)findViewById(R.id.friends_list);
		final ArrayAdapter<String> my_adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
		
		
		
		myPd_ring=ProgressDialog.show(Friends_page.this, "Please wait", "Loading please wait..", true);
		myPd_ring.setCancelable(true);
		new Thread(new Runnable() {  
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try
				{
					String our_Id=ParseUser.getCurrentUser().getUsername();
					ParseQuery friends =new ParseQuery("Friends");
					friends.whereEqualTo("UserId", our_Id);
					friends.findInBackground(new FindCallback() {
						
						@Override
						public void done(List<ParseObject> arg0, ParseException arg1) {
							my_adapter.clear();
							// TODO Auto-generated method stub
							for(ParseObject obj:arg0)
							{
								my_adapter.add(obj.getString("FriendId"));
							}
							
							my_adapter.notifyDataSetChanged();
							friends_list.setAdapter(my_adapter);
							myPd_ring.cancel();
						}
					});
 

					Thread.sleep(5000);
				}catch(Exception e){}

				//				myPd_ring.cancel();
			}
		}).start();
		
		
		friends_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				
				String friend = friends_list.getItemAtPosition(position).toString();
				ParseQuery location = ParseUser.getQuery();
				location.whereEqualTo("username", friend);
				try {
					loc_friend=location.find();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				for(ParseObject obj:loc_friend)
				{
					lat=obj.getString("Latitude");
					lon=obj.getString("Longitude");
					Time =  obj.getString("Time");
					
				}
//				DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm");
//				String new_Time = df.format(Time);
				Toast.makeText(getApplicationContext(), "Time ="+Time, Toast.LENGTH_SHORT).show();
				
				Intent i = new Intent(Friends_page.this,G_Maps_Friends.class);
				i.putExtra("lat", lat);
				i.putExtra("lon", lon);
				i.putExtra("time", Time);
				startActivity(i);
				
				
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//		super.onBackPressed();
		 new AlertDialog.Builder(Friends_page.this)
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
}
