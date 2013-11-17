package com.example.foodtruck;

import android.util.Log;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;

public class FoodTruck {
	
	public int id;
	public String truckName;
	public String category;
	public String description;
	public String imageName;
	
	// the next two are the paypal information
	//private String ppClientId;
	//private String ccEnv;
	
	public FoodTruck() {
		
	}
	
	public void insertIntoDB(MobileServiceClient mClient){
		mClient.getTable(FoodTruck.class).insert(this, new TableOperationCallback<FoodTruck>() {
		      public void onCompleted(FoodTruck entity, Exception exception, ServiceFilterResponse response) {
		            if (exception == null) {
		            	Log.w("success", "FoodTruck object created!");
		            	Log.w("id of the food truck", entity.id + " ");
		            } else {
		                  // Insert failed
		            	exception.printStackTrace();
		            }
		      }
		});
	}

}
