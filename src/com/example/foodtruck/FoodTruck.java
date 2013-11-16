package com.example.foodtruck;

import java.util.UUID;

public class FoodTruck {
	
	private UUID id;
	private String truckName;
	private String category;
	// the next two are the paypal information
	private String ppClientId;
	private String ccEnv;
	
	public FoodTruck(String name, String category, String clientId, String env){
		id = UUID.randomUUID();
		truckName = name;
		this.category = category;
		ppClientId = clientId;
		ccEnv = env;
	}

}
