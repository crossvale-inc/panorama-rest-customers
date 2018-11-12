package com.crossvale.fiscamel.service.datatypes;

public class CodeValue {
	
	private String code;
	private String description;
	
	public CodeValue() {
		
	}
	
	public CodeValue(String code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
