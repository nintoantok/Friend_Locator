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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class Notification_new extends Activity {

	ArrayAdapter<String> noti_adapter;
	ProgressDialog myPd_ring;
	ListView list_notification;
	TextView Noti_text;
	ArrayList<ParseObject> obj_notification = new ArrayList<ParseObject>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification);
		noti_adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1);
		list_notification = (ListView)findViewById(R.id.list_notification);
		Noti_text = (TextView)findViewById(R.id.Noti_text);

		myPd_ring=ProgressDialog.show(Notification_new.this, "Please wait", "Loading please wait..", true);
		myPd_ring.setCancelable(true);
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				try {
					ParseQuery myquery = new ParseQuery("Request");
					myquery.whereEqualTo("UserId", ParseUser.getCurrentUser().getUsername());
					myquery.findInBackground(new FindCallback() {

						@Override
						public void done(List<ParseObject> arg0, ParseException arg1) {
							// TODO Auto-generated method stub
							for(ParseObject obj:arg0)
							{
								noti_adapter.add(obj.getString("RequestingId"));
								obj_notification.add(obj);
							}

							noti_adapter.notifyDataSetChanged();

							if(noti_adapter.isEmpty())
							{
								Noti_text.setText("No Notifications!");
							}

							list_notification.setAdapter(noti_adapter);


							myPd_ring.dismiss();

						}
					});

					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}).start();





		list_notification.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, final int position, long arg3)
			{
				// TODO Auto-generated method stub

				AlertDialog.Builder builder = new AlertDialog.Builder(Notification_new.this);
				builder.setMessage("Would you like to add "+noti_adapter.getItem(position)+" as your friend?")
				.setCancelable(false)

				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
//						String our_Name = ParseUser.getCurrentUser().getUsername();			
//						ParseObject Request = new ParseObject("Request");
						
						
//							Request.put("UserId",Adapter.getItem(position));
//							Request.put("RequestingId", our_Name);
//							Request.saveInBackground();
//							Toast.makeText(getApplicationContext(), "Request sent", Toast.LENGTH_SHORT).show();
						
						String our_Name = ParseUser.getCurrentUser().getUsername();
						ParseObject Request = new ParseObject("Friends");
						Request.put("UserId",our_Name);
						Request.put("FriendId", noti_adapter.getItem(position));
						Request.saveEventually();
						ParseObject Request_relation = new ParseObject("Friends");
						Request_relation.put("UserId", noti_adapter.getItem(position));
						Request_relation.put("FriendId", our_Name);
						Request_relation.saveInBackground();
						Toast.makeText(getApplicationContext(), "You are now friends with "+noti_adapter.getItem(position), Toast.LENGTH_SHORT).show();
							
						
					}
				})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
					
				});
				AlertDialog alert = builder.create();
				alert.show();
				
				obj_notification.get(position).deleteEventually();
				
			}
		});



	}

}
