package com.example.foodtruck;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.util.Log;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.TableQueryCallback;


public class TruckStop {
	
	int id;
	String biddingEndTime;
	String truckArrivalTime;
	String location;
	
	//FoodTruck foodTruck;
	
	// location
	double longitude;
	double latitude;
	
	// associated food truck information
	public int truckId;
	public String truckName;
	public String description;
	public String imageName;
	
	
	double amount = 0.0;
	
	
	public TruckStop(){
		
		//populate pledges arrayList by querying the pledges relation where pledgeStopId = truckId
		
		
	}
	
	public TruckStop(String stopID, String truckID) {
		//Query Truck table
		
	}
	
	public void insertIntoDB(MobileServiceClient mClient){
		mClient.getTable(TruckStop.class).insert(this, new TableOperationCallback<TruckStop>() {
		      public void onCompleted(TruckStop entity, Exception exception, ServiceFilterResponse response) {
		            if (exception == null) {
		                  Log.w("success", "Truck Stop Object created!");
		            } else {
		                  // Insert failed
		            	exception.printStackTrace();
		            }
		      }
		});
	}
	
	public Double getAmountPledged(MobileServiceClient mClient){
		
		mClient.getTable(Pledge.class).where().field("truckStopId").eq(truckId).execute(new TableQueryCallback<Pledge>() {
            public void onCompleted(List<Pledge> result, int count, Exception exception, ServiceFilterResponse response) {
            	if (exception == null) {
          		  for (Pledge p : result) {
          			  amount += p.getAmount();
          		  }
          	  } 
          	  else {
          		  exception.printStackTrace();
          	  }
            }
		});
		return amount;
	}
	
	
}
