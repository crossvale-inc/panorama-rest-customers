package com.crossvale.fiscamel.service.datatypes;

public class Entity {
	
	private String entityType;
	private String entityNumber;
	
	public Entity() {
		
	}
	
	public Entity(String type, String number) {
		this.entityType = type;
		this.entityNumber = number;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getEntityNumber() {
		return entityNumber;
	}

	public void setEntityNumber(String entityNumber) {
		this.entityNumber = entityNumber;
	}
}
