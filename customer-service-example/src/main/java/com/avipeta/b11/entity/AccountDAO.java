package com.avipeta.b11.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.avipeta.b11.model.AccountHoldingType;
import com.avipeta.b11.model.AccountType;

@Entity
@Table(name = "account")
public class AccountDAO {
	
	 @Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 private Long id;
	 
	 @ManyToOne
	 @JoinColumn
	 private CustomerDAO primarycustomer;
	 
	 @NotNull(message = "accountnumber may not be empty")
	 @Column(unique = true)
	 private Long accountnumber;
	 
	 @Enumerated(EnumType.STRING)
	 private AccountType accountype;
	 
	 @Enumerated(EnumType.STRING)
   	 private AccountHoldingType accountholdingtype;
	 
	 @NotNull(message = "balance may not be empty")
	 private double balance = 0.0;
	
	 public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CustomerDAO getPrimarycustomer() {
		return primarycustomer;
	}

	public void setPrimarycustomer(CustomerDAO primarycustomer) {
		this.primarycustomer = primarycustomer;
	}

	public Long getAccountnumber() {
		return accountnumber;
	}

	public void setAccountnumber(Long accountnumber) {
		this.accountnumber = accountnumber;
	}

	public AccountType getAccountype() {
		return accountype;
	}

	public void setAccountype(AccountType accountype) {
		this.accountype = accountype;
	}

	public AccountHoldingType getAccountholdingtype() {
		return accountholdingtype;
	}

	public void setAccountholdingtype(AccountHoldingType accountholdingtype) {
		this.accountholdingtype = accountholdingtype;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

}
