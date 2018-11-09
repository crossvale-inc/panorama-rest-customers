package com.crossvale.fiscamel.service.datatypes;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Account {
	
	@JsonProperty("id")
	private String accountNumber;
	private String customerNumber;
	private String status;
    private String label;
    @JsonProperty("bank_id")
    private String bankId;
    private Money balance;
    private String type;
    @JsonProperty("account_routing")
    private List<AccountRouting> accountRouting;
    @JsonProperty("account_rules")
    private List<AccountRule> accountRules;
	
	public Account() {
		balance = new Money();
		accountRouting = new ArrayList<AccountRouting>();
		accountRules = new ArrayList<AccountRule>();
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}
	
	public Money getBalance() {
		return balance;
	}

	public void setBalance(Money balance) {
		this.balance = balance;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<AccountRouting> getAccountRouting() {
		return accountRouting;
	}

	public void setAccountRouting(List<AccountRouting> accountRouting) {
		this.accountRouting = accountRouting;
	}
	
	public String getAccountRoutingAsString() {
		String accountRoutingString = "";
		int listSize = accountRouting.size();
		System.out.println("AccountRouting array length: " + listSize);
		int lastIndex = listSize - 1;
		for (int i = 0; i < listSize; i++) {
			String routeToAdd = accountRouting.get(i).getScheme() + ":" + accountRouting.get(i).getAddress();
			accountRoutingString = accountRoutingString.concat(routeToAdd);
			if (i < lastIndex) {
				accountRoutingString = accountRoutingString.concat(",");
			}
		}
		System.out.println("AccountRoutingString: " + accountRoutingString);
		return accountRoutingString;
	}
	
	public void setAccountRoutingFromString(String accountRoutingString) {
		if (accountRoutingString.isEmpty()) {
			return;
		}
		if (accountRoutingString.contains(",")) {
			String[] routingStrings = accountRoutingString.split(",");
			for (String route : routingStrings) {
				String[] routeParts = route.split(":");
				AccountRouting routingNumber = new AccountRouting(routeParts[0], routeParts[1]);
				accountRouting.add(routingNumber);
			}
		} else {
			String[] routeParts = accountRoutingString.split(":");
			AccountRouting routingNumber = new AccountRouting(routeParts[0], routeParts[1]);
			accountRouting.add(routingNumber);
		}
	}

	public List<AccountRule> getAccountRules() {
		return accountRules;
	}

	public void setAccountRules(List<AccountRule> accountRules) {
		this.accountRules = accountRules;
	}
	
	public String getAccountRulesAsString() {
		String accountRoutingString = "";
		int listSize = accountRouting.size();
		int lastIndex = listSize - 1;
		for (int i = 0; i < listSize; i++) {
			String routeToAdd = accountRules.get(i).getScheme() + ":" + accountRules.get(i).getValue();
			accountRoutingString = accountRoutingString.concat(routeToAdd);
			if (i < lastIndex) {
				accountRoutingString = accountRoutingString.concat(",");
			}
		}
		return accountRoutingString;
	}
	
	public void setAccountRulesFromString(String accountRulesString) {
		if (accountRulesString.isEmpty()) {
			return;
		}
		if (accountRulesString.contains(",")) {
			String[] ruleStrings = accountRulesString.split(",");
			for (String rule : ruleStrings) {
				String[] ruleParts = rule.split(":");
				AccountRule accountRule = new AccountRule(ruleParts[0], ruleParts[1]);
				accountRules.add(accountRule);
			}
		} else {
			String[] ruleParts = accountRulesString.split(":");
			AccountRule accountRule = new AccountRule(ruleParts[0], ruleParts[1]);
			accountRules.add(accountRule);
		}
	}
	
	public static class AccountRouting {

		private String scheme;
		private String address;
		
		public AccountRouting() {
			
		}
		
		public AccountRouting(String scheme, String address) {
			this.scheme = scheme;
			this.address = address;
		}

		public String getScheme() {
			return scheme;
		}

		public void setScheme(String scheme) {
			this.scheme = scheme;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}
		
	}
	
	public static class AccountRule {

		private String scheme;
		private String value;
		
		public AccountRule() {
			
		}
		
		public AccountRule(String scheme, String value) {
			this.scheme = scheme;
			this.value = value;
		}

		public String getScheme() {
			return scheme;
		}

		public void setScheme(String scheme) {
			this.scheme = scheme;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}
	}

}