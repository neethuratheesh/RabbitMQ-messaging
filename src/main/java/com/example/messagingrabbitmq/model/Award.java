package com.example.messagingrabbitmq.model;

import java.util.List;

import org.springframework.stereotype.Component;
@Component
public class Award {
	
	private String awardNumber;
	private List<String> stores;
	private String message;
	
	public String getAwardNumber() {
		return awardNumber;
	}
	public void setAwardNumber(String awardNumber) {
		this.awardNumber = awardNumber;
	}
	public List<String> getStores() {
		return stores;
	}
	public void setStores(List<String> stores) {
		this.stores = stores;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	@Override
	public String toString() {
		return "AwardNumber: " +" "+awardNumber +"Stores: " +stores +" " +"Message: " +message; 
		
	}

}
