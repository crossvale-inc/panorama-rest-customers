package com.crossvale.fiscamel.service.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.model.FromDefinition;
import org.apache.camel.spring.SpringCamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import org.springframework.stereotype.Service;

import com.crossvale.fiscamel.service.datatypes.Customer;
import com.crossvale.fiscamel.service.datatypes.Address;

@Service("customerController")
public class CustomerController_MQ {

	public CustomerController_MQ() {
		
	}

	public Customer getCustomerByNumber(String[] headers, String body) {
		
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
	
	public Customer unknownOperation(String[] headers, String body) {
		
		//todo: fix this
		Customer customer = new Customer();
		customer.setCustomerNumber("-1");
		return customer;
	}
	

}
