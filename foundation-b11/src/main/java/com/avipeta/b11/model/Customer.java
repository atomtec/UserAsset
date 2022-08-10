package com.avipeta.b11.model;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

public class Customer {
	
	
	private Long id = 0L;
	
	@Valid
	@NotEmpty(message = "First name may not be empty")
	String firstName;
	
	@Valid
	@NotEmpty(message = "Last name may not be empty")
	String lastName;

	@Valid
	@NotEmpty(message = "Pan number may not be empty")
	String panNumber;

	@Valid
	@NotEmpty(message = "Email id may not be empty")
	String emailId;
	
	@Valid
	@NotEmpty(message = "Address may not be empty")
	String address;
	
	@Valid
	@NotEmpty(message = "PhoneNumber may not be empty")
	String phoneNumber;

	private Set<Account> accounts = null;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	

}
