package com.crossvale.fiscamel.service.datatypes;

import java.util.ArrayList;
import java.util.List;

public class Response {
	
	private String status;
	private List<Entity> entities;
	private List<CodeValue> errors;
	
	public Response() {
		entities = new ArrayList<Entity>();
		errors = new ArrayList<CodeValue>();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Entity> getEntities() {
		if (entities == null) {
			entities = new ArrayList<Entity>();
		}
		return entities;
	}

	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

	public List<CodeValue> getErrors() {
		if (errors == null) {
			errors = new ArrayList<CodeValue>();
		}
		return errors;
	}

	public void setErrors(List<CodeValue> errors) {
		this.errors = errors;
	}

}