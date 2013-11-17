package com.example.foodtruck;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class UserLocationsView extends Activity {
	GoogleMap map;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//no android header
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		//Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_user_locations_view);
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setMyLocationEnabled(true);
		
		map.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker arg0) {
				
				String snippet = arg0.getSnippet();
				//LayoutInflater vi = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				//View v = vi.inflate(R.layout.your_layout, null);
			
				
				
				return false;
			}
			
			
		});
		addMarkers();
		map.addMarker(new MarkerOptions().position(new LatLng(0,0)).title("Hello World").snippet("45/55$/Bid Now!"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_locations_view, menu);
		return true;
	}
	
	public void addMarkers(){
		// query database
		//range values to query
		LatLng myLatLng = map.getCameraPosition().target;
		double myLat = myLatLng.latitude;
		double myLng = myLatLng.longitude;
		
		// query from myLat +- 5, myLng +-5
		
		//retrieve objects from database, store in array
		//iterate over array
		
		map.addMarker(new MarkerOptions().position(new LatLng(0,0)).title("Hello World").snippet("45/55$/Bid Now!"));
	}

}
