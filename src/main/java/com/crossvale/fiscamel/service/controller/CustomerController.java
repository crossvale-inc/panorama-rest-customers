package com.crossvale.fiscamel.service.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.crossvale.fiscamel.service.datatypes.Customer;
import com.crossvale.fiscamel.service.datatypes.CustomerBase;
import com.crossvale.fiscamel.service.datatypes.Event;
import com.crossvale.fiscamel.service.model.CustomerModel;

@Service("customerController")
public class CustomerController {

	@Autowired
	private ApplicationContext appContext;
	
	private CustomerModel customerModel;
	private ResponseController responseController;

	public CustomerController() {
		
	}

	
	/**
	 * Get all customers
	 * @return Collection of CustomerBase representing all customers found by the CustomerModel
	 */
	public Collection<CustomerBase> getAllCustomers() {
		List<CustomerBase> customers = new ArrayList<CustomerBase>();
		// Like all methods, we need to get the bean for the CustomerModel to call methods
		customerModel = (CustomerModel) appContext.getBean("customerModel");
		// We get the EventController bean the same way to be able to send events
		responseController = (ResponseController) appContext.getBean("responseController");
		// Send an event about the GET call being performed
		responseController.sendEventAboutGet(new Event("GET", "Customers", ""));
		// Call the selectAllCustomers method from the CustomerModel bean to get all the customers
		customers = customerModel.selectAllCustomers();
		
		return customers;
	}
	
	/**
	 * Get customer specified by customerNumber
	 * @param customerNumber
	 * @return Customer from the CustomerModel with the specified customerNumber
	 */
	public Customer getCustomerByNumber(String customerNumber) {
		Customer customer = new Customer();
		customerModel = (CustomerModel) appContext.getBean("customerModel");
		responseController = (ResponseController) appContext.getBean("responseController");
		
		// We need to get the accountController so we can access the method to add accounts to the Customer
		AccountController accountController = (AccountController) appContext.getBean("accountController");
		
		// Send an event about the GET call being performed
		responseController.sendEventAboutGet(new Event("GET", "Customer by CustomerNumber", ""));
		// Next, we get the customer from the CustomerModel
		customer = customerModel.selectCustomerByNumber2(customerNumber);
		// Then we set the accounts on the customer based on the list returned from the AccountController
		customer.setAccounts(accountController.getListOfAccountsByCustomerNumber(customer.getCustomerNumber()));
		
		return customer;
	}
	
	/**
	 * Search for a customer by the customerName
	 * @param customerName
	 * @return Collection of CustomerBase of all customers matching the customerName search parameter
	 */
	public Collection<CustomerBase> searchCustomerByName(String customerName) {
		List<CustomerBase> customers = new ArrayList<CustomerBase>();
		customerModel = (CustomerModel) appContext.getBean("customerModel");
		responseController = (ResponseController) appContext.getBean("responseController");
		
		// Send an event about the GET call being performed
		responseController.sendEventAboutGet(new Event("GET", "Search customer by name", ""));
		// Get the customers from the CustomerModel
		customers = customerModel.selectCustomerLikeCustomerName(customerName);
		
		return customers;
	}
	
	/**
	 * Add a new customer to the CustomerModel
	 * @param customer
	 * @return customerNumber as returned by the CustomerModel insertCustomer method
	 * @throws SQLException 
	 */
	public String addCustomer(Customer customer) throws SQLException {
		customerModel = (CustomerModel) appContext.getBean("customerModel");
		return customerModel.insertCustomer(customer);
	}
	
	/** 
	 * Modify an existing customer in the CustomerModel
	 * @param customer
	 * @throws SQLException 
	 */
	public void modifyCustomer(Customer customer) throws SQLException {
		customerModel = (CustomerModel) appContext.getBean("customerModel");
		customerModel.updateCustomer(customer);
	}

}
