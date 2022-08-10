package com.avipeta.b11.model;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class Account {

	private Long id;


	private Long accountNumber;

	@Valid
	@Enumerated(EnumType.STRING)
	private AccountType accounType = AccountType.SAVINGS;

	@Valid
	@Enumerated(EnumType.STRING)
	private AccountHoldingType accountHoldingType = AccountHoldingType.INDIVIDUAL;

	@Valid
	@NotNull(message = "balance may not be empty")
	private double balance = 0.0;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public AccountType getAccounType() {
		return accounType;
	}

	public void setAccounType(AccountType accounType) {
		this.accounType = accounType;
	}

	public AccountHoldingType getAccountHoldingType() {
		return accountHoldingType;
	}

	public void setAccountHoldingType(AccountHoldingType accountHoldingType) {
		this.accountHoldingType = accountHoldingType;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

}
