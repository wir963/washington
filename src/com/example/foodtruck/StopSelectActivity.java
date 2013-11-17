package com.example.foodtruck;

import java.net.MalformedURLException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;


public class StopSelectActivity extends Activity {

	private MobileServiceClient mClient;
	private int truckId = 1;// assuming the food truck user is food truck where id == 1

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stop_select);
		
		//Remove title bar
				//this.requestWindowFeature(Window.FEATURE_NO_TITLE);

				//Remove notification bar
				//this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


		// connect to the Azure MobileServiceClient
		try {
			mClient = new MobileServiceClient("https://gettruckedup.azure-mobile.net/", "djHwmBvRUfXjnAYNCAnPawmlcHQNrx41", this);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		mClient.getTable(TruckStop.class).where().field("truckId").eq(truckId).execute(new TableQueryCallback<TruckStop>() {
			public void onCompleted(final List<TruckStop> result, int count, Exception exception, ServiceFilterResponse response) {
				if (exception == null) {					
					menuAdapter adapter = new menuAdapter(getApplicationContext(), result);
					ListView lView = (ListView) findViewById(R.id.list);
					//lView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
					lView.setAdapter(adapter);
				}
				else {
					exception.printStackTrace();
				}
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stop_select, menu);
		getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}


	public class menuAdapter extends BaseAdapter {


		private Context currentContext;
		private List<TruckStop> stopList;

		public menuAdapter(Context inputContext, List<TruckStop> inputTruckStops) {

			currentContext = inputContext;
			stopList = inputTruckStops;
		}

		@Override
		public int getCount() {
			return stopList.size();
		}

		@Override
		public Object getItem(int position) {
			return stopList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup parent) {

			LayoutInflater inflater = (LayoutInflater) currentContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View outputView;
			//List<String> arrayStops(); // = new List<String>();
			//{275, 400, 100, 124};

			if(convertView == null) {
				outputView = new View(currentContext);
				outputView = inflater.inflate(R.layout.list_row, null);

				TextView locationTitle = (TextView) outputView.findViewById(R.id.list_title);
				TextView locationAmount = (TextView) outputView.findViewById(R.id.list_amount);
				Button selectLocationButton = (Button) outputView.findViewById(R.id.selectLocationButton); // Button for when the food truck driver wants to decide on the location (s)he's going to be at today.
				selectLocationButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent launchTruckConfirmationView = new Intent(getApplicationContext(), TruckConfirmation.class);
						//launchTruckConfirmationView.putExtra("", stopList.get(position).id);
						startActivity(launchTruckConfirmationView);
					}});// end of onClickListener
				TruckStop ts = stopList.get(position);
				locationTitle.setText(stopList.get(position).location); // Text description of this location

				locationAmount.setText(stopList.get(position).getAmountPledged(mClient) + "");  
				//locationAmount.setText(arrayStops.get(position) + "");  
			}
			else
			{
				outputView = (RelativeLayout)convertView;
			}

			return outputView;
		}


	}

}
