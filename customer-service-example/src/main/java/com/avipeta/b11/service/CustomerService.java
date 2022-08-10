package com.avipeta.b11.service;

import java.util.List;

import com.avipeta.b11.model.Account;
import com.avipeta.b11.model.BalanceTransfer;
import com.avipeta.b11.model.Customer;



public interface CustomerService {

	Customer create(Customer customer) throws Exception;
	Customer update(Customer customer,Long id) throws Exception;
	Customer deleteCustomerById(long id) throws Exception;
	List<Customer> getAllCustomers() throws Exception;
	Customer getCustomerById(Long id) throws Exception;
	void deleteAllCustomers() throws Exception;
	//long addAccount(Customer customer)throws Exception;
	public Customer addAccountById(Long customerid, Account account) throws Exception;
	//long updateAccount(Customer customer) throws Exception;
	//long updateAccountBalance(Customer customer) throws Exception;
	Customer transferBalance(BalanceTransfer balance) throws Exception;
	Customer updateAccountById(Long customerid, long accountid, Account account) throws Exception;
	Customer updateAccountBalanceByAccNo(long customerid, long accountNumber, double fundAmount) throws Exception;

}
