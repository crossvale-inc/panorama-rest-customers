package com.crossvale.fiscamel.service.datatypes;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Identification {
	private String ssn;
	private String citizenFlag;
	private String idType;
	private String idNumber;
	private String issuedLocation;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Timestamp issuedDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
	private Timestamp expDate;
	
	public Identification() {
		// Empty constructor
	}
	
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getCitizenFlag() {
		return citizenFlag;
	}
	public void setCitizenFlag(String citizenFlag) {
		this.citizenFlag = citizenFlag;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getIssuedLocation() {
		return issuedLocation;
	}
	public void setIssuedLocation(String issuedLocation) {
		this.issuedLocation = issuedLocation;
	}
	public Timestamp getIssuedDate() {
		return issuedDate;
	}
	public void setIssuedDate(Timestamp issuedDate) {
		this.issuedDate = issuedDate;
	}
	public Timestamp getExpDate() {
		return expDate;
	}
	public void setExpDate(Timestamp expDate) {
		this.expDate = expDate;
	}
}
