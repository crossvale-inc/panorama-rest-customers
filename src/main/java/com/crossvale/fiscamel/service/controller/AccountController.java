package com.crossvale.fiscamel.service.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.crossvale.fiscamel.service.datatypes.Account;
import com.crossvale.fiscamel.service.model.AccountModel;

@Service("accountController")
public class AccountController {

	@Autowired
	private ApplicationContext appContext;
	
	public AccountController() {

	}

	/**
	 * Get all of the accounts associated with a customer specified by customerNumber
	 * @param customerNumber
	 * @return List of AccountBase that have the matching customerNumber
	 */
	public List<Account> getListOfAccountsByCustomerNumber(String customerNumber) {
		List<Account> accounts = new ArrayList<Account>();
		AccountModel accountModel = (AccountModel) appContext.getBean("accountModel");
		accounts = accountModel.selectAccountsByCustomerNumber(customerNumber);
		return accounts;
	}
	
}
