package com.example.foodtruck;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class UserLocationsView extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_locations_view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_locations_view, menu);
		return true;
	}

}
