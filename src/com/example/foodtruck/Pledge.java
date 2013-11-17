package com.example.foodtruck;

import java.util.UUID;

public class Pledge {
	
	private Double amount;
	private UUID id;
	private User user;
	
	public Pledge(Double amount, UUID id, User user){
		this.amount = amount;
		this.id = id;
		this.user = user;
	}
	
	public Double getAmount(){
		return amount;
	}

}
