package com.example.foodtruck;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.github.sendgrid.SendGrid;


public class PayConfirm extends Activity{
	
	protected static final String EXTRA_PAYMENT_AMOUNT = null;
	protected static final String EXTRA_CLIENT_EMAIL = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		//Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		//Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		super.onCreate(savedInstanceState);
		final TextView emailText = (TextView) findViewById(R.id.emailText);
		emailText.setText("Give us your email so we can tell you if your food truck is coming to you!");

		emailText.setVisibility(View.VISIBLE);
		
		final TextView doneText = (TextView) findViewById(R.id.doneText);
		doneText.setText("Awesome!  We'll let you know if you getTrucked!");
		doneText.setVisibility(View.GONE);
		
		setContentView(R.layout.pay_confirm_layout);
		
		final EditText userEmailField = (EditText) findViewById(R.id.userEmail);
		final String userEmail = userEmailField.getText().toString();
		
		final Button button = (Button) findViewById(R.id.submitUserEmailButton);

		button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				sendEmailNow(userEmail);
				
				//create email object for user, then change text/button to complete
				//return to homescreen
				//Intent moveToMenuSelection = new Intent(getApplicationContext(), Recipient_Selection.class); //will launch the menuSelection application
				//startActivity(moveToMenuSelection);
				emailText.setVisibility(View.INVISIBLE);
				userEmailField.setVisibility(View.INVISIBLE);
				button.setVisibility(View.INVISIBLE);
				doneText.setVisibility(View.VISIBLE);

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pay_confirm, menu);
		return true;
	}
	
	protected void sendEmailNow(String userEmail){
		SendGrid sendgrid = new SendGrid("janetzhu", "ledian3722");

		sendgrid.addTo(userEmail);
		sendgrid.setFrom("getTrucked@gmail.com");
		sendgrid.setSubject("Thank you for your payment");  
		sendgrid.setText("Thank you for your $" + EXTRA_PAYMENT_AMOUNT + " bid for the foodtruck Mexicana.  "
				+ "We will let you know by 11:00am whether the truck comes to your locatin.");
		sendgrid.send();
	}
	

	
	public void twilioTest() {

		HttpClient twilioClient = new DefaultHttpClient();

		HttpPost twilioPost = new HttpPost("https://api.twilio.com/2010-04-01/Accounts/AC4f435a0df4049be99ab6adfd4f23d253/SMS/Messages.json");
		List<NameValuePair> values = new ArrayList<NameValuePair>(2);
		values.add(new BasicNameValuePair("account_sid", "AC4f435a0df4049be99ab6adfd4f23d253"));
		values.add(new BasicNameValuePair("AuthToken","4155a7091d9e77ce670b978fd0badd47"));
		values.add(new BasicNameValuePair("From","12024173418"));
		values.add(new BasicNameValuePair("To","12023403171"));
		values.add(new BasicNameValuePair("Body","Welcome to Battlehack 2013 - Bacon Bacon Bacon"));
		try {
			twilioPost.setEntity(new UrlEncodedFormEntity(values));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Making HTTP Request
		try {
		    HttpResponse response = twilioClient.execute(twilioPost);
		    
		 
		    // writing response to log
		    Log.d("Http Response:", response.toString());
		    Log.d("Status Code", response.getStatusLine().toString());
		 
		} catch (ClientProtocolException e) {
		    // writing exception to log
		    e.printStackTrace();
		         
		} catch (IOException e) {
		    // writing exception to log
		    e.printStackTrace();
		}
		}
	
}


