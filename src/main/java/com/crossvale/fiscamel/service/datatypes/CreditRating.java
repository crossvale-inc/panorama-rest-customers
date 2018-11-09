package com.crossvale.fiscamel.service.datatypes;

public class CreditRating {
	
	private String rating;
	private String source;
	
	public CreditRating() {
		
	}
	
	public CreditRating(String rating, String source) {
		this.rating = rating;
		this.source = source;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
