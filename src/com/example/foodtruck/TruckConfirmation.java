package com.example.foodtruck;

import com.github.sendgrid.SendGrid;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TruckConfirmation extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_truck_confirmation);
		
		new Thread(new Runnable() {
		    public void run() {
		      sendEmailLater("anhiancheong@gmail.com");
		    }
		  }).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.truck_confirmation, menu);
		
	
		return true;

	}

	
	protected void sendEmailLater(String userEmail){
		SendGrid sendgrid = new SendGrid("janetzhu", "ledian3722");
		sendgrid.addTo(userEmail);
		sendgrid.setFrom("getTrucked@gmail.com");
		sendgrid.setSubject("You gotTRUCKED!");  
		sendgrid.setText("Mexicana is coming to your location from 12:00-2:00pm!  Head on over to redeem your $" + 5);
		sendgrid.send();
	}
}
