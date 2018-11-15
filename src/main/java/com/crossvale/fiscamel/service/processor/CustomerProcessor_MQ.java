package com.crossvale.fiscamel.service.processor;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.crossvale.fiscamel.service.controller.CustomerController;
import com.crossvale.fiscamel.service.datatypes.CodeValue;
import com.crossvale.fiscamel.service.datatypes.Customer;
import com.crossvale.fiscamel.service.datatypes.Address;
import com.crossvale.fiscamel.service.datatypes.Entity;
import com.crossvale.fiscamel.service.datatypes.Response;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component 
public class CustomerProcessor_MQ implements Processor {

	@Autowired
	private ApplicationContext appContext;
	
	@Override
	public void process(Exchange exchange) throws Exception {
		System.out.println("CustomerProcessor_MQ");
		
		// Get the message as a string from the exchange body
		String message = exchange.getIn().getBody(String.class);
		
		// Get the HTTP method from the header so we can determine what to do with the data
		String httpmethod = (String)exchange.getIn().getHeader(Exchange.HTTP_METHOD);
		String operation = (String)exchange.getIn().getHeader("operation");
		// Set a header value on the exchange so that we can use it later in processing
		exchange.getIn().setHeader("eventDataType", "Customer");
		
		// Ignore the other possible HTTP methods that could lead here
		if (httpmethod.equals("OPTIONS")) {
			return;			
		}
		
		if(httpmethod.equals("GET") && operation == "getCustomerById")
		{
			Customer customer = new Customer();
			Address address = new Address();
			
			customer.setCustomerNumber("0001");
			customer.setLegalName("Jimmy Dean, A Sausage Company");
			customer.setEmail("jimmy.dean@crossvale.com");
			address.setAddress1("123 Sesame St.");
			address.setCity("Elmoville");
			address.setState("TX");
			address.setPostalCode("75759");
			customer.setAddress(address);
			exchange.getIn().setBody(customer);
		}
		else if(httpmethod.equals("GET"))
		{
			Customer customer = new Customer();
			customer.setCustomerNumber("-1");
			exchange.getIn().setBody(customer);
		}
		else
		{
		
		
		
		// We use the ObjectMapper to map the message JSON string to the Customer class
		ObjectMapper mapper = new ObjectMapper();
		Response response = new Response();
        try {
        	// Get the customer from the message, and the customerContrller bean so we can use its methods
			Customer customer = mapper.readValue((String) message, Customer.class);
			CustomerController customerController = (CustomerController)appContext.getBean("customerController");
			/* For a POST, we add the customer and set the returned customerNumber to the header we set previously for 
			* later processing (when we return to the user and send the event to the AMQ Broker)
			* A PUT simply modifies the customer with the provided data */
			if (httpmethod.equals("POST")){
				String customerNumber = customerController.addCustomer(customer);
				response.getEntities().add(new Entity("customerNumber", customerNumber));
			} else if (httpmethod.equals("PUT")) {
				customerController.modifyCustomer(customer);
				response.getEntities().add(new Entity("customerNumber", customer.getCustomerNumber()));
			}
		} catch (SQLException sqlex) {
			response.setStatus("FAILURE");
			response.getErrors().add(new CodeValue("SQL-Error-Code", String.valueOf(sqlex.getErrorCode())));
			response.getErrors().add(new CodeValue("SQL-State", sqlex.getSQLState()));
			response.getErrors().add(new CodeValue("SQL-Error-Message", sqlex.getMessage()));
		} catch (IOException e) {
			response.setStatus("FAILURE");
			response.getErrors().add(new CodeValue("IOException-Message", e.getMessage()));
		} finally {
			exchange.setProperty("response", response);
		}
		}
		
	}
	

	/*
	public Message getCustomerByNumber(@Headers Map headers, @Body String body) {
		
		Customer customer = new Customer();
		Address address = new Address();
		
		customer.setCustomerNumber("0001");
		customer.setLegalName("Jimmy Dean, A Sausage Company");
		customer.setEmail("jimmy.dean@crossvale.com");
		address.setAddress1("123 Sesame St.");
		address.setCity("Elmoville");
		address.setState("TX");
		address.setPostalCode("75759");
		customer.setAddress(address);
				
		return customer;		
	}
	*/
	
	/*
	  public Customer unknownOperation(@Headers Map headers, @Body String body) {
	 
		
		//todo: fix this
		Customer customer = new Customer();
		customer.setCustomerNumber("-1");
		return customer;
	*/
}
