package com.crossvale.fiscamel.service.datatypes;

import java.util.ArrayList;
import java.util.List;

public class Customer extends CustomerBase {

	private List<Account> accounts;
	
	public Customer() {
        super();
        this.accounts = new ArrayList<Account>();
    }
	
	public List<Account> getAccounts() {
		return accounts;
	}
	
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
}
