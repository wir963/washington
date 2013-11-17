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
		exampleMethod();
		
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
				Intent launchTruckLocationView = new Intent(getApplicationContext(), StopListViewMultipleSelectionActivity.class);
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
		ts.longitude = -121.6;
		ts.truckId = 1;
		ts.truckName = "Blue Saigon";
		ts.description = "Blue Saigon Truck is owned and operated by Kiem Nguyen. He is also the head chef of this truck and took his time to master each food item on the menu. Mr. Nguyen will not serve food to customers if he won't eat it himself. Blue Saigon Truck is new and different!";
		ts.biddingEndTime = "12:00 PM";
		ts.truckArrivalTime = "3:00 PM";
		ts.location = "Mitchell Drive & Oak Grove Rd";
		ts.imageName = "blue_saigon";
		ts.insertIntoDB(mClient);
		
		TruckStop ts2 = new TruckStop();
		ts2.latitude = 37.4;
		ts2.longitude = -121.8;
		ts2.truckId = 1;
		ts2.truckName = "Bacon, Bacon";
		ts2.description = "The bacon food truck for San Francisco features bacon on a burger, bacon dipped in chocolate, a pork belly sandwich and bacon jam for added bacon deliciousness.";
		ts2.biddingEndTime = "11:30 AM";
		ts2.truckArrivalTime = "2:00 PM";
		ts2.location = "1710 Mission Street, San Francisco, CA";
		ts2.imageName = "bacon";
		ts2.insertIntoDB(mClient);
		
		TruckStop ts3 = new TruckStop();
		ts3.latitude = 37.6;
		ts3.longitude = -122.3;
		ts3.truckId = 1;
		ts3.truckName = "Gold Rush Eatery";
		ts3.description = "Gold Rush Eatery roams the streets of San Jose serving up quality burgers, sandwiches, and of course, their 'Golden Nugget' tater tots. Gold Rush Eatery's signature sandwich is the The Prospector; made with Tri Tip, mushrooms, and green onions in a sherrry wine sauce. Check in often for additions to the menu such as root beer floats and clam chowder, and definetly give their Bermuda Onion Rings a try.";
		ts3.biddingEndTime = "11:00 AM";
		ts3.truckArrivalTime = "2:00 PM";
		ts3.location = "300 Rio Robles E, San Jose, CA";
		ts3.imageName = "goldrush";
		ts3.insertIntoDB(mClient);

		TruckStop ts4 = new TruckStop();
		ts4.latitude = 37.6;
		ts4.longitude = -122.3;
		ts4.truckId = 1;
		ts4.truckName = "The Waffle Roost";
		ts4.description = "Welcome to the Bay Area's own Chicken & Waffles Truck! We pair (a slightly spicy) traditional buttermilk fried chicken with a true Belgian waffle. We mix and match them in all sorts of ways. Pair that combo with some traditional Southern sides and you've been introduced to the Roost.";
		ts4.biddingEndTime = "10:00 AM";
		ts4.truckArrivalTime = "12:00 PM";
		ts4.location = "300 Rio Robles E, San Jose, CA";
		ts4.imageName = "waffle_roost";
		ts4.insertIntoDB(mClient);
		
	}

}
