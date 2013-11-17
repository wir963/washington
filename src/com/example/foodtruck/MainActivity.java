package com.example.foodtruck;

import java.net.MalformedURLException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

import com.microsoft.windowsazure.mobileservices.*;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainActivity extends Activity {
	
	private MobileServiceClient mClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//remove android title
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		//Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_main);
		
		try {
			mClient = new MobileServiceClient("https://gettruckedup.azure-mobile.net/", "djHwmBvRUfXjnAYNCAnPawmlcHQNrx41", this);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		// Go to Food Truck activity
		//exampleMethod();
		
		// Go to user locations view activity
		
		ImageButton goToUserLocationViewButton = (ImageButton) findViewById(R.id.userViewButton);
		goToUserLocationViewButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent launchUserLocationView = new Intent(getApplicationContext(), UserLocationsView.class);
				startActivity(launchUserLocationView);
			}});// end of onClickListener
		
		ImageButton goToTruckViewButton = (ImageButton) findViewById(R.id.truckViewButton);
		goToTruckViewButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent launchTruckLocationView = new Intent(getApplicationContext(), StopSelectActivity.class);
				startActivity(launchTruckLocationView);
			}});// end of onClickListener
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void exampleMethod() {
		
		TruckStop ts = new TruckStop();
		ts.latitude = 37.3;
		ts.longitude = -121.9;
		ts.truckId = 1;
		ts.truckName = "Tacos, Tacos, Tacos";
		ts.description = "Really good tacos";
		ts.biddingEndTime = "11:00";
		ts.truckArrivalTime = "2:00";
		ts.location = "Corner of Central Park";
		ts.imageName = "ic_launcher";
		ts.insertIntoDB(mClient);
		
	}

}
