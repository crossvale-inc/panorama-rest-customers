package com.crossvale.fiscamel.service.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.crossvale.fiscamel.service.datatypes.CreditLimit;
import com.crossvale.fiscamel.service.datatypes.CreditRating;
import com.crossvale.fiscamel.service.datatypes.Customer;
import com.crossvale.fiscamel.service.datatypes.CustomerBase;

@Service("customerModel")
public class CustomerModel {

	private Connection connection = null;
	private SQLException latestException = new SQLException();

	/**
	 * Empty constructor for the CustomerModel that verifies we have the driver we
	 * need for postgresql
	 */
	public CustomerModel() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Select all customers from the customer table
	 * 
	 * @return List of CustomerBase representing all customers in the customer table
	 */
	public List<CustomerBase> selectAllCustomers() {
		// With every database call, we initalize the connection to the database before
		// proceeding
		initializeConnection();

		// Start with empty list of CustomerBase
		List<CustomerBase> customers = new ArrayList<CustomerBase>();
		if (connection == null) {
			// Return the empty list if the database connection doesn't initialize correctly
			return customers;
		}

		try {
			PreparedStatement ps = connection.prepareStatement("select * from open_customer;");
			ResultSet rs = ps.executeQuery();

			/*
			 * Loop through each ResultSet, retrieving each customer and cloning to a
			 * CustomerBase before adding each to the list
			 */
			while (rs.next()) {
				Customer customer = retrieveCustomer(rs);
				customers.add(customer.cloneToBase());
			}
			// Always close the connection after the call is performed
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return customers;
	}

	
	/**
	 * Select the customer that contains the matching customerNumber
	 * 
	 * @param customerNumber
	 * @return Customer that contains the matching customerNumber
	 */
	public Customer selectCustomerByNumber2(String customerNumber) {
		//initializeConnection();
		//String sql = "select * from open_customer where customer_number like ?;";
		Customer foundCustomer = new Customer();
		foundCustomer.setCustomerNumber(customerNumber);
		return foundCustomer;
		/*
		if (connection == null) {
			return foundCustomer;
		}

		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, customerNumber);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				foundCustomer = retrieveCustomer(rs);
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return foundCustomer;
		*/
	}
	
	
	/**
	 * Select the customer that contains the matching customerNumber
	 * 
	 * @param customerNumber
	 * @return Customer that contains the matching customerNumber
	 */
	public Customer selectCustomerByNumber(String customerNumber) {
		initializeConnection();
		String sql = "select * from open_customer where customer_number like ?;";
		Customer foundCustomer = new Customer();
		if (connection == null) {
			return foundCustomer;
		}

		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ps.setString(1, customerNumber);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				foundCustomer = retrieveCustomer(rs);
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return foundCustomer;
	}

	/**
	 * Select customer(s) whose customerName(s) contain(s) the name search string
	 * 
	 * @param name
	 * @return List of CustomerBase of customers found by the search
	 * @throws SQLException
	 */
	public List<CustomerBase> selectCustomerLikeCustomerName(String name) {
		initializeConnection();
		// Comparison is 'ilike' as that allows for case insensitivity
		String sql = "select * from open_customer where legal_name ilike ?;";
		List<CustomerBase> customers = new ArrayList<CustomerBase>();
		if (connection == null) {
			return customers;
		}
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			// Put search parameter between '%' to allow search within name
			ps.setString(1, "%" + name + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Customer customer = retrieveCustomer(rs);
				customers.add(customer.cloneToBase());
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customers;
	}

	/**
	 * Insert a new customer into the database
	 * 
	 * @param customer
	 * @return customerNumber representing the next value in the database customer
	 *         sequence
	 * @throws SQLException
	 */
	public String insertCustomer(Customer customer) throws SQLException {
		initializeConnection();
		if (connection == null) {
			throw latestException;
		}
		// Query to get the next value in the database customer sequence
		String getNumberSql = "SELECT nextval('customer_sequence');";
		String customerNumber = "";
		String sql = "INSERT INTO open_customer VALUES (currval('customer_sequence'), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,"
				+ "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
		PreparedStatement ps0 = connection.prepareStatement(getNumberSql);
		ResultSet rs = ps0.executeQuery();
		if (rs.next()) {
			customerNumber = rs.getString("nextval");
		}
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, customer.getUserId());
		ps.setString(2, customer.getLegalName());
		ps.setString(3, customer.getMobilePhoneNumber());
		ps.setString(4, customer.getEmail());
		ps.setTimestamp(5, customer.getDateOfBirth());
		ps.setString(6, customer.getRelationshipStatus());
		ps.setString(7, customer.getCreditRating().getRating());
		ps.setString(8, customer.getCreditRating().getSource());
		ps.setString(9, customer.getCreditLimit().getCurrency());
		ps.setString(10, customer.getCreditLimit().getAmount());
		ps.setString(11, customer.getHighestEducationAttained());
		ps.setString(12, customer.getEmploymentStatus());
		ps.setString(13, customer.getKycStatus());
		ps.setTimestamp(14, customer.getLastOkDate());
		ps.setString(15, customer.getAddress().getAddress1());
		ps.setString(16, customer.getAddress().getAddress2());
		ps.setString(17, customer.getAddress().getCity());
		ps.setString(18, customer.getAddress().getState());
		ps.setString(19, customer.getAddress().getPostalCode());
		ps.setString(20, customer.getAddress().getCountry());
		ps.setString(21, customer.getIdentification().getSsn());
		ps.setString(22, customer.getIdentification().getCitizenFlag());
		ps.setString(23, customer.getIdentification().getIdType());
		ps.setString(24, customer.getIdentification().getIdNumber());
		ps.setString(25, customer.getIdentification().getIssuedLocation());
		ps.setTimestamp(26, customer.getIdentification().getIssuedDate());
		ps.setTimestamp(27, customer.getIdentification().getExpDate());
		ps.executeUpdate();
		connection.close();
		return customerNumber;
	}

	/**
	 * Update the customer in the database with provided customer data
	 * 
	 * @param customer
	 * @throws SQLException
	 */
	public void updateCustomer(Customer customer) throws SQLException {
		initializeConnection();
		if (connection == null) {
			throw latestException;
		}
		String sql = "UPDATE open_customer set user_id = ?, legal_name = ?, mobile_phone_number = ?, email = ?, "
				+ "date_of_birth = ?, relationship_status = ?, credit_rating = ?, credit_rating_source = ?, "
				+ "credit_limit_currency = ?, credit_limit_amount = ?, highest_education_attained = ?, "
				+ "employment_status = ?, kyc_status = ?, last_ok_date = ?, address1 = ?, address2 = ?, city = ?, "
				+ "state = ?, postal_code = ?, country = ?, ssn = ?, citizen_flag = ?, id_type = ?, id_number = ?, "
				+ "issued_location = ?, issued_date = ?, exp_date = ? where customer_number like ?;";
		PreparedStatement ps = connection.prepareStatement(sql);
		ps.setString(1, customer.getUserId());
		ps.setString(2, customer.getLegalName());
		ps.setString(3, customer.getMobilePhoneNumber());
		ps.setString(4, customer.getEmail());
		ps.setTimestamp(5, customer.getDateOfBirth());
		ps.setString(6, customer.getRelationshipStatus());
		ps.setString(7, customer.getCreditRating().getRating());
		ps.setString(8, customer.getCreditRating().getSource());
		ps.setString(9, customer.getCreditLimit().getCurrency());
		ps.setString(10, customer.getCreditLimit().getAmount());
		ps.setString(11, customer.getHighestEducationAttained());
		ps.setString(12, customer.getEmploymentStatus());
		ps.setString(13, customer.getKycStatus());
		ps.setTimestamp(14, customer.getLastOkDate());
		ps.setString(15, customer.getAddress().getAddress1());
		ps.setString(16, customer.getAddress().getAddress2());
		ps.setString(17, customer.getAddress().getCity());
		ps.setString(18, customer.getAddress().getState());
		ps.setString(19, customer.getAddress().getPostalCode());
		ps.setString(20, customer.getAddress().getCountry());
		ps.setString(21, customer.getIdentification().getSsn());
		ps.setString(22, customer.getIdentification().getCitizenFlag());
		ps.setString(23, customer.getIdentification().getIdType());
		ps.setString(24, customer.getIdentification().getIdNumber());
		ps.setString(25, customer.getIdentification().getIssuedLocation());
		ps.setTimestamp(26, customer.getIdentification().getIssuedDate());
		ps.setTimestamp(27, customer.getIdentification().getExpDate());
		ps.setString(28, customer.getCustomerNumber());
		ps.executeUpdate();
		connection.close();
	}

	/**
	 * Retrieve the customer from the ResultSet returned from the database
	 * 
	 * @param rs
	 * @return Customer retrieved from the ResultSet
	 * @throws SQLException
	 */
	public Customer retrieveCustomer(ResultSet rs) throws SQLException {
		Customer c = new Customer();
		c.setUserId(rs.getString("user_id"));
		c.setCustomerNumber(rs.getString("customer_number"));
		c.setLegalName(rs.getString("legal_name"));
		c.setMobilePhoneNumber(rs.getString("mobile_phone_number"));
		c.setEmail(rs.getString("email"));
		c.setDateOfBirth(rs.getTimestamp("date_of_birth"));
		c.setRelationshipStatus(rs.getString("relationship_status"));
		c.setCreditRating(new CreditRating(rs.getString("credit_rating"), rs.getString("credit_rating_source")));
		c.setCreditLimit(new CreditLimit(rs.getString("credit_limit_currency"), rs.getString("credit_limit_amount")));
		c.setHighestEducationAttained(rs.getString("highest_education_attained"));
		c.setEmploymentStatus(rs.getString("employment_status"));
		c.setKycStatus(rs.getString("kyc_status"));
		c.setLastOkDate(rs.getTimestamp("last_ok_date"));
		c.getAddress().setAddress1(rs.getString("address1"));
		c.getAddress().setAddress2(rs.getString("address2"));
		c.getAddress().setCity(rs.getString("city"));
		c.getAddress().setState(rs.getString("state"));
		c.getAddress().setPostalCode(rs.getString("postal_code"));
		c.getAddress().setCountry(rs.getString("country"));
		c.getIdentification().setSsn(rs.getString("ssn"));
		c.getIdentification().setCitizenFlag(rs.getString("citizen_flag"));
		c.getIdentification().setIdType(rs.getString("id_type"));
		c.getIdentification().setIdNumber(rs.getString("id_number"));
		c.getIdentification().setIssuedLocation(rs.getString("issued_location"));
		c.getIdentification().setIssuedDate(rs.getTimestamp("issued_date"));
		c.getIdentification().setExpDate(rs.getTimestamp("exp_date"));

		return c;
	}

	/**
	 * Initialize the connection to the database
	 */
	public void initializeConnection() {
		try {
			// Retrieve the database URL, username, and password from the system
			// environments
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
			latestException = e;
		}
	}
}
