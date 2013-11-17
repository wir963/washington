package com.example.foodtruck;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import android.util.Log;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;


public class TruckStop {

	int stopId;
	int truckId;
	String biddingEndTime;
	String truckArrivalTime;
	int currentAmount;
	String location;
	
	FoodTruck foodTruck;
	
	double longitude;
	double latitude;
	
	Date endTime;
	
	
	// These are the IDs of the pledges
	ArrayList<Pledge> pledges = new ArrayList<Pledge>();
	int pledgeTotal = 0;
	
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

	public void populateTruckInfo() {
		// TODO Auto-generated method stub
		
		//get name and type of food truck by querying food truck table
		
	}

	public void populatePledgeInfo() {
		// TODO Auto-generated method stub
		
		//get array list of pledges, or just count up the totals
	}
	
	/*public Double getAmountPledged(){
		Double amount = 0.00;
		for (int i = 0; i < pledges.size(); i++){
			amount += pledges.get(i).getAmount();
		}
		return amount;
	}*/
	
	
}
