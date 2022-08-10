package com.avipeta.b11;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.avipeta.b11.entity.AccountDAO;
import com.avipeta.b11.entity.CustomerDAO;
import com.avipeta.b11.model.Account;
import com.avipeta.b11.model.AccountHoldingType;
import com.avipeta.b11.model.AccountType;
import com.avipeta.b11.model.Customer;

public class Utility {

	
	public static Customer convertCustomerDAOtoCustomer(CustomerDAO custDAO) {
		Customer customer = new Customer();
		customer.setAddress(custDAO.getAddress());
		customer.setEmailId(custDAO.getEmailid());
		customer.setFirstName(custDAO.getFirstname());
		customer.setId(custDAO.getId());
		customer.setLastName(custDAO.getLastname());
		customer.setPanNumber(custDAO.getPannumber());
		customer.setPhoneNumber(custDAO.getPhonenumber());
		customer.setAccounts(convertAccountDAOSettoAccountSet(custDAO.getAccounts()));
		return customer;
	}
	
	public static Set<Account> convertAccountDAOSettoAccountSet(Set<AccountDAO> accountsDAO) {
		if (accountsDAO == null)
			return null;
		Set<Account> accounts = new HashSet<>();
		for(AccountDAO accDAO: accountsDAO) {
			accounts.add(convertAccountDAOtoAccount(accDAO));
		}
		return accounts;
	}
	
	
	public static Account convertAccountDAOtoAccount(AccountDAO accDAO) {
		Account account = new Account();
		account.setAccountHoldingType(accDAO.getAccountholdingtype());
		account.setAccountNumber(accDAO.getAccountnumber());
		account.setAccounType(accDAO.getAccountype());
		account.setBalance(accDAO.getBalance());
		account.setId(accDAO.getId());
		return account;
	}
	
	public static <T> Set<T> convertListToSet(List<T> list) {
		// create a set from the List
		return new HashSet<>(list);
	}
	
	public static Customer getCustomer() {
		Customer cust = new Customer();
		cust.setPhoneNumber("9831645612");
		cust.setLastName("Gupta");
		cust.setEmailId("howtodoinjava@gmail.com");
		cust.setAddress("Gope Lane");
		cust.setFirstName("Lokesh");
		cust.setPanNumber("BCMPK");
		return  cust;
	}
	
	public static Account getAccount() {
		Account account = new Account();
		account.setAccountHoldingType(AccountHoldingType.INDIVIDUAL);
		account.setAccounType(AccountType.SAVINGS);
		account.setBalance(100.0);
		return account;
		
	}
}
