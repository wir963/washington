package com.example.foodtruck;

import java.net.MalformedURLException;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import com.microsoft.windowsazure.mobileservices.*;

public class MainActivity extends Activity {
	
	private MobileServiceClient mClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		try {
			mClient = new MobileServiceClient("https://gettruckedup.azure-mobile.net/", "djHwmBvRUfXjnAYNCAnPawmlcHQNrx41", this);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void exampleMethod() {
		/*Item item = new Item();
		item.Text = "Awesome item";
		mClient.getTable(Item.class).insert(item, new TableOperationCallback<Item>() {
		      public void onCompleted(Item entity, Exception exception, ServiceFilterResponse response) {
		            if (exception == null) {
		                  // Insert succeeded
		            } else {
		                  // Insert failed
		            }
		      }
		});*/
	}

}
