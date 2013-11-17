package com.example.foodtruck;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import android.util.Log;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.TableOperationCallback;


public class TruckStop {

	int id;
	int truckId;
	
	double longitude;
	double latitude;
	
	//ArrayList<Pledge> pledges = new ArrayList<Pledge>();
	
	public TruckStop(){
		
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
	
	/*public Double getAmountPledged(){
		Double amount = 0.00;
		for (int i = 0; i < pledges.size(); i++){
			amount += pledges.get(i).getAmount();
		}
		return amount;
	}*/
	
	
}
