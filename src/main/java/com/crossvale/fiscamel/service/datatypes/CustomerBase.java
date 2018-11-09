package com.crossvale.fiscamel.service.datatypes;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerBase {

	@JsonProperty("user_id")
	private String userId;
	@JsonProperty("customer_number")
	private String customerNumber;
	@JsonProperty("legal_name")
	private String legalName;
	@JsonProperty("mobile_phone_number")
	private String mobilePhoneNumber;
	private String email;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	@JsonProperty("date_of_birth")
	private Timestamp dateOfBirth;
	@JsonProperty("relationship_status")
	private String relationshipStatus;
	@JsonProperty("credit_rating")
	private CreditRating creditRating;
	@JsonProperty("credit_limit")
	private CreditLimit creditLimit;
	@JsonProperty("highest_education_attained")
	private String highestEducationAttained;
	@JsonProperty("employment_status")
	private String employmentStatus;
	@JsonProperty("kyc_status")
	private String kycStatus;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	@JsonProperty("last_ok_date")
	private Timestamp lastOkDate;
	private Address address;
	private Identification identification;

	public CustomerBase() {
		address = new Address();
		identification = new Identification();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(String customerNumber) {
		this.customerNumber = customerNumber;
	}

	public String getLegalName() {
		return legalName;
	}

	public void setLegalName(String legalName) {
		this.legalName = legalName;
	}

	public String getMobilePhoneNumber() {
		return mobilePhoneNumber;
	}

	public void setMobilePhoneNumber(String mobilePhoneNumber) {
		this.mobilePhoneNumber = mobilePhoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Timestamp getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Timestamp dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getRelationshipStatus() {
		return relationshipStatus;
	}

	public void setRelationshipStatus(String relationshipStatus) {
		this.relationshipStatus = relationshipStatus;
	}

	public CreditRating getCreditRating() {
		return creditRating;
	}

	public void setCreditRating(CreditRating creditRating) {
		this.creditRating = creditRating;
	}

	public CreditLimit getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(CreditLimit creditLimit) {
		this.creditLimit = creditLimit;
	}

	public String getHighestEducationAttained() {
		return highestEducationAttained;
	}

	public void setHighestEducationAttained(String highestEducationAttained) {
		this.highestEducationAttained = highestEducationAttained;
	}

	public String getEmploymentStatus() {
		return employmentStatus;
	}

	public void setEmploymentStatus(String employmentStatus) {
		this.employmentStatus = employmentStatus;
	}

	public String getKycStatus() {
		return kycStatus;
	}

	public void setKycStatus(String kycStatus) {
		this.kycStatus = kycStatus;
	}

	public Timestamp getLastOkDate() {
		return lastOkDate;
	}

	public void setLastOkDate(Timestamp lastOkDate) {
		this.lastOkDate = lastOkDate;
	}

	public Address getAddress() {
		if (address == null) {
			address = new Address();
		}
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Identification getIdentification() {
		if (identification == null) {
			identification = new Identification();
		}
		return identification;
	}

	public void setIdentification(Identification identification) {
		this.identification = identification;
	}

	public CustomerBase cloneToBase() {
		CustomerBase c = new CustomerBase();
		c.setUserId(userId);
		c.setCustomerNumber(customerNumber);
		c.setLegalName(legalName);
		c.setMobilePhoneNumber(mobilePhoneNumber);
		c.setEmail(email);
		c.setDateOfBirth(dateOfBirth);
		c.setRelationshipStatus(relationshipStatus);
		c.setCreditRating(creditRating);
		c.setCreditLimit(creditLimit);
		c.setHighestEducationAttained(highestEducationAttained);
		c.setEmploymentStatus(employmentStatus);
		c.setKycStatus(kycStatus);
		c.setLastOkDate(lastOkDate);
		c.setAddress(address);
		c.setIdentification(identification);
		return c;
	}

}
