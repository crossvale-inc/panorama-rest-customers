package com.crossvale.fiscamel.service.datatypes;

public class Event {
	
	private String restType;
	private String dataType;
	private String jsonBody;
	
	public Event() {
		
	}
	
	public Event(String restType, String dataType, String jsonBody) {
		this.restType = restType;
		this.dataType = dataType;
		this.jsonBody = jsonBody;
	}

	public String getRestType() {
		return restType;
	}

	public void setRestType(String restType) {
		this.restType = restType;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getJsonBody() {
		return jsonBody;
	}

	public void setJsonBody(String jsonBody) {
		this.jsonBody = jsonBody;
	}

}