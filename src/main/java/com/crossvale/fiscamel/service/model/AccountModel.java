package com.crossvale.fiscamel.service.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.crossvale.fiscamel.service.datatypes.Account;
import com.crossvale.fiscamel.service.datatypes.Money;

@Service("accountModel")
public class AccountModel {
	
	private Connection connection = null;
	
	/**
	 * Empty constructor for the AccountModel that verifies we have the driver we need for postgresql
	 */
	public AccountModel() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Select all accounts that match the specified customerNumber
	 * @param customerNumber
	 * @return List of accounts as AccountBase that match the specified customerNumber
	 */
	public List<Account> selectAccountsByCustomerNumber(String customerNumber) {
		// With every database call, we initialize the connection to the database before proceeding
		initializeConnection();
		
		// Create an empty list of accounts to start
		List<Account> accounts = new ArrayList<Account>();
		if (connection == null) {
			// Return empty list if database is not initialized correctly
			return accounts;
		}

		try {
			// Specify SQL string with wild cards (?) for variable replacement in a PreparedStatement
			String sql = "select * from open_account where customer_number like ?;";
			PreparedStatement ps = connection.prepareStatement(sql);
			
			// Replace the ? with the customerNumber
			ps.setString(1, customerNumber);
			ResultSet rs = ps.executeQuery();
			// For each result, retrieve the account from the ResultSet and add it to the list
			while (rs.next()) {
				Account account = retrieveAccount(rs);
				accounts.add(account);
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return accounts;
	}
	
	/**
	 * Retrieve the account from the resultSet
	 * @param rs ResultSet that the database returns
	 * @return Account retrieved from the ResultSet
	 * @throws SQLException
	 * @throws ParseException
	 */
	public Account retrieveAccount(ResultSet rs) throws SQLException {
		Account a = new Account();
		a.setAccountNumber(rs.getString("account_number"));
		a.setCustomerNumber(rs.getString("customer_number"));
		a.setStatus(rs.getString("status"));
		a.setLabel(rs.getString("label"));
		a.setBankId(rs.getString("bank_id"));
		a.setBalance(new Money(rs.getString("balance_amount"), rs.getString("balance_currency")));
		a.setType(rs.getString("type"));
		a.setAccountRoutingFromString(rs.getString("account_routing"));
		a.setAccountRulesFromString(rs.getString("account_rules"));
		return a;
	}

	/**
	 * Initialize the connection to the database
	 */
	public void initializeConnection() {
		try {
			// Retrieve the database URL, username, and password from the system environments
			String url, user, password;
			url = System.getenv("DB_URL");
			user = System.getenv("DB_USER");
			password = System.getenv("DB_PASS");
			
			// Set values to defaults if environment variables aren't set
			if (url == null) {
				url = "jdbc:postgresql://postgresql/panorama?stringtype=unspecified";
			}
			if (user == null) {
				user = "admin";
			}
			if (password == null) {
				password = "admin";
			}
			
			// Get the connection from the DriverManager
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
