package com.example.foodtruck;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.List;

import org.json.JSONException;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

public class UserLocationsView extends Activity implements LocationListener
{
	
	private static final String CONFIG_ENVIRONMENT = PaymentActivity.ENVIRONMENT_NO_NETWORK;

    private static final String CONFIG_CLIENT_ID = "AVZ0jRB7XveAdyS4VEkpY1-s4xUoLr-yo8hZ2U8pbGgM5vPW5EnYHzVBhGa-";

    private static final String CONFIG_RECEIVER_EMAIL = ""; 
    
    EditText pledgeAmount;
    
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
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		try {
			mClient = new MobileServiceClient("https://gettruckedup.azure-mobile.net/", "djHwmBvRUfXjnAYNCAnPawmlcHQNrx41", this);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Intent intent = new Intent(this, PayPalService.class);

	    // live: don't put any environment extra
	    // sandbox: use PaymentActivity.ENVIRONMENT_SANDBOX
	    intent.putExtra(PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT, PaymentActivity.ENVIRONMENT_NO_NETWORK);
	    intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, "AVZ0jRB7XveAdyS4VEkpY1-s4xUoLr-yo8hZ2U8pbGgM5vPW5EnYHzVBhGa-");
	    startService(intent);
		
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
				String stopID = arg0.getSnippet();
				
				selectedStop = getStopWithId(stopID);
				
				pledgeAmount = (EditText) findViewById(R.id.pledgeAmount);
		
				TextView truckName = (TextView) findViewById(R.id.truckName);
				truckName.setText(selectedStop.truckName);

				String uri = "drawable/" + selectedStop.imageName;

				int imageResource = getResources().getIdentifier(uri, null, getPackageName());

				ImageView truckImage = (ImageView) findViewById(R.id.truckImage);
				Drawable image = getResources().getDrawable(imageResource);
			    truckImage.setImageDrawable(image);
				
				TextView truckDescription = (TextView) findViewById(R.id.truckDescription);
				truckDescription.setText("Description: " + selectedStop.description);
				
				TextView truckLocation = (TextView) findViewById(R.id.truckLocation);
				truckLocation.setText("Location: " + selectedStop.location);
				
				TextView endTime = (TextView) findViewById(R.id.endTime);
				endTime.setText("Bidding End Time: " + selectedStop.biddingEndTime);
				
				TextView currentAmount = (TextView) findViewById(R.id.currentAmount);
				currentAmount.setText("Current Amount: " + selectedStop.getAmountPledged(mClient));
				
				return false;
			}

			private TruckStop getStopWithId(String stopID) {
				int intStopId =  Integer.parseInt(stopID);
				for(int i = 0; i < currentStopList.size(); i++) {
					if(intStopId == currentStopList.get(i).id) {
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
	
		Button payButton = (Button) findViewById(R.id.paypalButton);
		
		payButton.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View arg0) {
//		        Intent loginIntent = new Intent();
//				
//				EditText pledgeAmount = (EditText) findViewById(R.id.pledgeAmount);
//				
//				String pledgeAmt = pledgeAmount.getText().toString();
//				
				onBuyPressed(arg0);

			}});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_locations_view, menu);
		return true;
	}
	
	public void addMarkers(){
		
		// query the database for five TruckStops within 1/4 of a degree of longitude and latitude - later
		// grab all the Truck Stops out of the database
		mClient.getTable(TruckStop.class).execute(new TableQueryCallback<TruckStop>() {
              public void onCompleted(List<TruckStop> result, int count, Exception exception, ServiceFilterResponse response) {
            	  if (exception == null) {
            		  currentStopList = result;
            		  for (TruckStop ts : result) {
            			  //Log.w("TruckStop", ts.stopId + " ");
            			  map.addMarker(new MarkerOptions().position(new LatLng(ts.latitude,ts.longitude)).title(ts.truckName).snippet("" + ts.id));
            		  }
            		  currentStopList = result;
            	  } 
            	  else {
            		  exception.printStackTrace();
            	  }
              }
		});
		
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
	
	
	@Override
	public void onDestroy() {
	    stopService(new Intent(this, PayPalService.class));
	    super.onDestroy();
	}
	
	///PAYPAL STUFF
	
	public void onBuyPressed(View pressed) {
		 PayPalPayment newPayment = new PayPalPayment(new BigDecimal(pledgeAmount.getText().toString()), "USD", "Pledge Amount");
	        
	        Intent intent = new Intent(this, PaymentActivity.class);
	        
	        intent.putExtra(PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT, CONFIG_ENVIRONMENT);
	        intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, CONFIG_CLIENT_ID);
	        intent.putExtra(PaymentActivity.EXTRA_RECEIVER_EMAIL, CONFIG_RECEIVER_EMAIL);
	        
	        intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID, "AVZ0jRB7XveAdyS4VEkpY1-s4xUoLr-yo8hZ2U8pbGgM5vPW5EnYHzVBhGa-");
	        intent.putExtra(PaymentActivity.EXTRA_PAYER_ID, "your-customer-id-in-your-system");
	        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, newPayment);
	        
	        startActivityForResult(intent, 0);
	}

	@Override
	protected void onActivityResult (int requestCode, int resultCode, Intent data) {
	    if (resultCode == Activity.RESULT_OK) {
	        PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
	        if (confirm != null) {
	            try {
	                Log.i("paymentExample", confirm.toJSONObject().toString(4));

	                // TODO: send 'confirm' to your server for verification.
	                // see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
	                // for more details.	                

	            } catch (JSONException e) {
	                Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
	            }
	        }
	    }
	    else if (resultCode == Activity.RESULT_CANCELED) {
	        Log.i("paymentExample", "The user cancelled.");
	    }
	    else if (resultCode == PaymentActivity.RESULT_PAYMENT_INVALID) {
	        Log.i("paymentExample", "An invalid payment was submitted. Please see the docs.");
	    }
	    
	    
		//Intent intent = new Intent(getApplicationContext(), PayConfirm.class); //will launch the menuSelection application
		//startActivity(intent);
		
	}


}
