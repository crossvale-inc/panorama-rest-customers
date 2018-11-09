package com.crossvale.fiscamel.service.datatypes;

public class CreditScore {
	
	private int score;
	private String rating;
	
	public CreditScore() {
		
	}
	
	public CreditScore(int score) {
		this.score = score;
		String rating = "";
		if (score >= 750) {
			rating = "A";
		} else if (score >= 700 && score < 750) {
			rating = "B";
		} else if (score >= 650 && score < 700) {
			rating = "C";
		} else if (score >= 550 && score < 650) {
			rating = "D";
		} else {
			rating = "F";
		}
		this.rating = rating;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}
}
