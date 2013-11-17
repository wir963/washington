package com.example.foodtruck;

import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class WelcomeScreen extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		//Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_welcome_screen);
		
		int DELAY = 3000;

	    Handler handler = new Handler();
	    handler.postDelayed(new Runnable() {            
	        @Override
	        public void run() {
	            Intent startSelectionPage = new Intent(getApplicationContext(), MainActivity.class);
	            startActivity(startSelectionPage);                  
	        }
	    }, DELAY);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.welcome_screen, menu);
		return true;
	}

}
