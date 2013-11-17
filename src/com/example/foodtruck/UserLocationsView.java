package com.example.foodtruck;

import java.net.MalformedURLException;
import java.util.List;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;

public class UserLocationsView extends Activity implements LocationListener
{
	GoogleMap map;
	protected LocationListener locationListener;
	protected LatLng myLatLng;
	protected LocationManager lm;
	protected Location location;
	private MobileServiceClient mClient;
	
	protected List<TruckStop> currentStopList;
	protected TruckStop selectedStop;
	
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
				String stopID = snippet;
				
				selectedStop = getStopWithId(stopID);
				selectedStop.populateTruckInfo();
				selectedStop.populatePledgeInfo();
		
				TextView truckInfo = (TextView) findViewById(R.id.truckInfo);
				truckInfo.setText("Name: " + selectedStop.foodTruck.truckName + " Type: " + selectedStop.foodTruck.category);
				
				return false;
			}

			private TruckStop getStopWithId(String stopID) {
				// TODO Auto-generated method stub
				int intStopId =  Integer.parseInt(stopID);
				for(int i = 0; i < currentStopList.size(); i++) {
					if(intStopId == currentStopList.get(i).stopId) {
						return currentStopList.get(i);
					}
					
				}
				return null;
			}
		});
		lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// grab the current location
		getCurrentLocation();
		// zoom into the current location
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, 10));
		addMarkers();
		//map.addMarker(new MarkerOptions().position(new LatLng(0,0)).title("Hello World").snippet("45/55$/Bid Now!"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_locations_view, menu);
		return true;
	}
	
	public void addMarkers(){
		// connect to the Azure MobileServiceClient
		try {
			mClient = new MobileServiceClient("https://gettruckedup.azure-mobile.net/", "djHwmBvRUfXjnAYNCAnPawmlcHQNrx41", this);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// query the database for five TruckStops within 1/4 of a degree of longitude and latitude - later
		// grab all the Truck Stops out of the db
		mClient.getTable(TruckStop.class).execute(new TableQueryCallback<TruckStop>() {
              public void onCompleted(List<TruckStop> result, int count, Exception exception, ServiceFilterResponse response) {
            	  if (exception == null) {
            		  for (TruckStop ts : result) {
            			  Log.w("TruckStop", ts.stopId + " ");
            			  map.addMarker(new MarkerOptions().position(new LatLng(ts.latitude,ts.longitude)).title(ts.truckId + " ").snippet("" + ts.stopId));
            		  }
            		  currentStopList = result;
            	  } 
            	  else {
            		  exception.printStackTrace();
            	  }
              }
		});
		
		//map.addMarker(new MarkerOptions().position(new LatLng(0,0)).title("Hello World").snippet("45/55$/Bid Now!"));
	}
	
	public void getCurrentLocation() 
	{
		location=lm.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
		Log.w("latitude", location.getLatitude() + " ");
		Log.w("longitude", location.getLongitude() + " ");
		if (location != null) {
			myLatLng = new LatLng(location.getLatitude(), location.getLongitude());
		}


	}

	@Override
	public void onLocationChanged(Location location) {
		myLatLng = new LatLng(location.getLatitude(), location.getLongitude());
	}
	 
	@Override
	public void onProviderDisabled(String provider) {
		Log.d("Latitude","disable");
	}
	 
	@Override
	public void onProviderEnabled(String provider) {
		Log.d("Latitude","enable");
	}
	 
	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Log.d("Latitude","status");
	}


}
