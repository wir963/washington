package com.example.foodtruck;

import com.github.sendgrid.SendGrid;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class TruckConfirmation extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_truck_confirmation);
		setContentView(R.layout.activity_truck_confirmation);
		
		
		//Remove title bar
				//this.requestWindowFeature(Window.FEATURE_NO_TITLE);

				//Remove notification bar
				//this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		
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
