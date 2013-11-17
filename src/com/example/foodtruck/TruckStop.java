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
	String name;// intersection of .../ Central Park and ...
	
	// location
	double longitude;
	double latitude;
	// date
	Date startTime;
	Date endTime;
	
	// associated food truck information
	int truckId;
	String truckDescription;
	String truckName;
	
	ArrayList<Pledge> pledges = new ArrayList<Pledge>();
	
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
		
		Double amount = 0.00;
		mClient.getTable(Pledge.class).where().field("truckStopId").eq(id).execute(new TableQueryCallback<Pledge>() {
            public void onCompleted(List<Pledge> result, int count, Exception exception, ServiceFilterResponse response) {
            	if (exception == null) {
          		  for (Pledge p : result) {
          			  pledges.add(p);
          		  }
          	  } 
          	  else {
          		  exception.printStackTrace();
          	  }
            }
		});
		for (int i = 0; i < pledges.size(); i++){
			amount = pledges.get(i).getAmount();
		}
		return amount;
	}
	
	
}
