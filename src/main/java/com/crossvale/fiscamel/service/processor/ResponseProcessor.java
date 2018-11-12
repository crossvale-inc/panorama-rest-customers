package com.crossvale.fiscamel.service.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.crossvale.fiscamel.service.datatypes.Response;

@Component
public class ResponseProcessor implements Processor {
	
	public void process(Exchange exchange) throws Exception {
		System.out.println("ResponseProcessor");
		
		// Create the event, and set its values based on headers that were set previously
		Response response = exchange.getProperty("response", Response.class);
		
		// Set the body of the exchange to the Event object
		exchange.getIn().setBody(response, Response.class);

	}

}
