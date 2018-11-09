package com.crossvale.fiscamel.service.controller;

import org.apache.camel.ProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.crossvale.fiscamel.service.datatypes.Event;

@Service("responseController")
@Component
public class ResponseController {

	@Autowired
	private ProducerTemplate producerTemplate;

	/**
	 * Send event details to the activemq event queue
	 * @param message to be sent
	 */
	public void sendEventDetails(String message) {
		System.out.println("sending a message to MQ");
		try {
			producerTemplate.sendBody("activemq:event", message);
		} catch (Exception e) {
			System.out.println("Caught exception sending to ActiveMQ...please check the broker");
		}
	}

	/**
	 * Send event about a GET request
	 * @param event to be sent
	 */
	public void sendEventAboutGet(Event event) {
		ObjectMapper mapper = new ObjectMapper();
		String message;
		try {
			// Convert the Event object to a json string
			message = mapper.writeValueAsString(event);
		} catch (JsonProcessingException e) {
			// Write a basic json string manually if we get an exception
			message="{'restType' : 'GET', 'dataType' : '" + event.getDataType() + "', 'jsonBody' : '" + 
					event.getJsonBody() + "'}";
		}
		System.out.println("sending a message to MQ");
		try {
			producerTemplate.sendBody("activemq:event", message);
		} catch (Exception e) {
			System.out.println("Caught exception sending to ActiveMQ...please check the broker");
		}
	}

}
