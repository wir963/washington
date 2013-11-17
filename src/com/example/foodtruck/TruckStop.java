package com.example.foodtruck;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;


public class TruckStop {

	FoodTruck truck;
	
	// will later break into longitude/latitude or something better
	String gPSCoordinate;
	
	ArrayList<Pledge> pledges = new ArrayList<Pledge>();
	
	public TruckStop(FoodTruck ft, String location){
		truck = ft;
		gPSCoordinate = location;
	}
	
	public Double getAmountPledged(){
		Double amount = 0.00;
		for (int i = 0; i < pledges.size(); i++){
			amount += pledges.get(i).getAmount();
		}
		return amount;
	}
}
