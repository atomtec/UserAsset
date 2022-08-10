package com.avipeta.b11.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.avipeta.b11.Utility;
import com.avipeta.b11.entity.AccountDAO;
import com.avipeta.b11.entity.CustomerDAO;
import com.avipeta.b11.exception.BadRequestException;
import com.avipeta.b11.exception.InvalidArgumentException;
import com.avipeta.b11.exception.ResourceNotFoundException;
import com.avipeta.b11.model.Account;
import com.avipeta.b11.model.BalanceTransfer;
import com.avipeta.b11.model.Customer;
import com.avipeta.b11.repository.AccountRepo;
import com.avipeta.b11.repository.CustomerRepo;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
    AccountRepo accountRepo;
	
	@Autowired
    CustomerRepo customerRepo;

	@Override
	public Customer create(Customer customer) throws Exception {
		return save(customer);
	}

	@Override
	public Customer update(Customer customer,Long id) throws Exception {
		try {
			CustomerDAO cusDAO = getCustomerDAOById(id);
			return update(cusDAO,customer,id);
		} catch (DataAccessException ex) {
			throw new Exception(ex);
		}
		

	}

	@Override
	public Customer deleteCustomerById(long id) throws Exception {
		Customer cust = validateAndGetExistingCustomerbyId(id);
		try {
			customerRepo.deleteById(id);
		} catch (DataAccessException ex) {
			throw new Exception(ex);
		}
		return cust;
	}

	@Override
	public List<Customer> getAllCustomers() throws Exception {
		List<Customer> customers = new ArrayList<>();
		try {
			List<CustomerDAO> customerDAO = customerRepo.findAll();
			for (CustomerDAO custDAO : customerDAO) {
				List<AccountDAO> accountDAOs = accountRepo.findByPrimarycustomer(custDAO);
				custDAO.setAccounts(Utility.convertListToSet(accountDAOs));
				customers.add(Utility.convertCustomerDAOtoCustomer(custDAO));
			}
		}
		catch (DataAccessException ex) {
			throw new Exception(ex);
		}
		return customers;
	}

	

	

	@Override
	public void deleteAllCustomers() throws Exception {
		try
		{
			customerRepo.deleteAll();
		}
		catch (DataAccessException ex)
		{
			throw new Exception(ex);
		}
		
		
	}
	
	private Customer save(Customer customer ) throws Exception
	{
		CustomerDAO custDAO = new CustomerDAO();
		custDAO.setAddress(customer.getAddress());
		custDAO.setFirstname(customer.getFirstName());
		custDAO.setLastname(customer.getLastName());
		custDAO.setPhonenumber(customer.getPhoneNumber());
		custDAO.setPannumber(customer.getPanNumber());
		custDAO.setEmailid(customer.getEmailId());
		
		try
		{
			customerRepo.save(custDAO);
		}
		catch (DataAccessException ex)
		{
			throw new Exception(ex);
		}
		
		customer.setId(custDAO.getId());
		return customer;
	}
	
	private Customer update(CustomerDAO custDAO ,Customer customer , long id ) throws Exception
	{
		
		custDAO.setAddress(customer.getAddress());
		custDAO.setFirstname(customer.getFirstName());
		custDAO.setLastname(customer.getLastName());
		custDAO.setPhonenumber(customer.getPhoneNumber());
		custDAO.setPannumber(customer.getPanNumber());
		custDAO.setEmailid(customer.getEmailId());
		if(id != 0L) {
			custDAO.setId(id);
		}
		try
		{
			customerRepo.save(custDAO);
		}
		catch (DataAccessException ex)
		{
			throw new Exception(ex);
		}
		
		customer.setId(custDAO.getId());
		return customer;
	}


	@Override
	public Customer getCustomerById(Long id) throws Exception {
		try {
			Optional<CustomerDAO> custDAOop = customerRepo.findById(id);
			if (custDAOop.isEmpty()) {
				throw new ResourceNotFoundException("Customer not found for this id :: " + id);
			} 
			else {
				//When we get customer details we get their account data to it too
				List<AccountDAO> accountDAOs = accountRepo.findByPrimarycustomer(custDAOop.get());
				if(accountDAOs.size() > 0)
				{
					custDAOop.get().setAccounts(Utility.convertListToSet(accountDAOs));
				}
				
				return Utility.convertCustomerDAOtoCustomer(custDAOop.get());
			}
		} catch (DataAccessException ex) {
			throw new Exception(ex);
		}
		
	}
	

	private CustomerDAO getCustomerDAOById(Long id) throws Exception {
		try {
			Optional<CustomerDAO> custDAOop = customerRepo.findById(id);
			if (custDAOop.isEmpty()) {
				throw new ResourceNotFoundException("Customer not found for this id :: " + id);
			} 
			else {
				return custDAOop.get();
			}
		} catch (DataAccessException ex) {
			throw new Exception(ex);
		}
		
	}
	


	/*
	 * @Override public long addAccount(Customer customer) throws Exception { //
	 * Check id first validateAndGetExistingCustomerbyId(customer.getId());
	 * Set<Account> accounts = customer.getAccounts(); if (accounts == null) throw
	 * new BadRequestException("No account data sent"); try { CustomerDAO custDAO =
	 * customerRepo.findById(customer.getId()).get(); // TODO do model convert
	 * rather than DB // calls for (Account acc : accounts) { AccountDAO accDAO =
	 * new AccountDAO(); accDAO.setAccountholdingtype(acc.getAccountHoldingType());
	 * accDAO.setAccountype(acc.getAccounType());
	 * accDAO.setAccountnumber(get9digitAccountNumber());
	 * accDAO.setBalance(acc.getBalance()); accDAO.setPrimarycustomer(custDAO);
	 * accountRepo.save(accDAO); } } catch (DataAccessException ex) { throw new
	 * Exception(ex); }
	 * 
	 * return customer.getId();
	 * 
	 * }
	 */
	
	
	@Override
	public Customer addAccountById(Long customerid, Account account) throws Exception {
		// Check id first
		Customer cust = validateAndGetExistingCustomerbyId(customerid);
		Set<Account> accounts = new HashSet<>();
		try {
			CustomerDAO custDAO = customerRepo.findById(customerid).get(); // TODO do model convert rather than DB
			AccountDAO accDAO = new AccountDAO();
			accDAO.setAccountholdingtype(account.getAccountHoldingType());
			accDAO.setAccountype(account.getAccounType());
			accDAO.setAccountnumber(get9digitAccountNumber());
			accDAO.setBalance(account.getBalance());
			accDAO.setPrimarycustomer(custDAO);
			accountRepo.save(accDAO);
			account.setId(accDAO.getId());
			account.setAccountNumber(accDAO.getAccountnumber());
			accounts.add(account);// calls		
		} catch (DataAccessException ex) {
			throw new Exception(ex);
		}
		cust.setAccounts(accounts);
		return cust;

	}
	
	/*
	 * @Override public long updateAccountBalance(Customer customer) throws
	 * Exception { // Need to validate exiting accounts
	 * validateAndGetExistingCustomerbyId(customer.getId()); Set<Account> accounts =
	 * customer.getAccounts(); if (accounts == null || accounts.isEmpty())// No
	 * account details from Restclient side throw new
	 * BadRequestException("No account data sent"); // Now get this customer's
	 * details from DB and match account try { CustomerDAO custDAO =
	 * customerRepo.findById(customer.getId()).get(); Set<AccountDAO> accDAOs =
	 * Utility.convertListToSet(accountRepo.findByPrimarycustomer(custDAO)); if
	 * (accDAOs.isEmpty()) // Ensure DB customers has account throw new
	 * InvalidArgumentException("This customer has no accounts Invalid Input" +
	 * customer.getId()); boolean match = false; for (Account acc : accounts) { for
	 * (AccountDAO accDAO : accDAOs) { if ((accDAO.getId() == acc.getId()) &&
	 * (acc.getAccountNumber() == accDAO.getAccountnumber())) { match = true; if
	 * (acc.getBalance() < 0.0) { // withdrawing balance double leftOverBlaance =
	 * accDAO.getBalance() + acc.getBalance(); if (leftOverBlaance > 0) {
	 * accDAO.setBalance(leftOverBlaance); } else throw new
	 * InvalidArgumentException("Cannot withdraw balance " + " Current balance is "
	 * + accDAO.getBalance() + "withdraw request is " + acc.getBalance()); } else {
	 * // Adding balance double addedBalance = accDAO.getBalance() +
	 * acc.getBalance(); accDAO.setBalance(addedBalance); }
	 * accountRepo.save(accDAO); } }
	 * 
	 * }
	 * 
	 * if (!match)// so customer id is valid but accounts don't match bad data throw
	 * new InvalidArgumentException("Account Data mismatch" + customer.getId()); }
	 * catch (DataAccessException ex) { throw new Exception(ex); } return
	 * customer.getId(); }
	 */
	
	
	
	@Override
	public Customer updateAccountBalanceByAccNo(long customerid, long accountNumber , double fundAmount) throws Exception {
		// Need to validate exiting accounts
		Customer cust = validateAndGetExistingCustomerbyId(customerid);
		AccountDAO accDAO = validateAndGetExistingAccountDAObyAccountNumber(accountNumber);
		Set<Account> accounts = new HashSet<>(); 
		if (fundAmount == 0.0)// No account details from Restclient side
			throw new InvalidArgumentException("Fund Transfer 0 is not Valid");
		// Now get this customer's details from DB and match account
		try {
			CustomerDAO custDAO = customerRepo.findById(customerid).get();//TODO translate model
			Set<AccountDAO> accDAOs = Utility.convertListToSet(accountRepo.findByPrimarycustomer(custDAO));
			if (accDAOs.isEmpty()) // Ensure DB customers has account
				throw new InvalidArgumentException("This customer has no accounts Invalid Input" + customerid);
			//Check for Ownership
			if(accDAO.getPrimarycustomer().getId() != customerid)
				throw new InvalidArgumentException("This customer id " + customerid + " does not won account number " + accountNumber);
			
			double updatedBalance = accDAO.getBalance() + fundAmount;  //funAmount has to be negative for withdrawal
			if(updatedBalance < 0)
				throw new InvalidArgumentException("Cannot withdraw balance " + " Current balance is "
						+ accDAO.getBalance() + "withdraw request is " + fundAmount);
			accDAO.setBalance(updatedBalance);
			accountRepo.save(accDAO);
			accounts.add(Utility.convertAccountDAOtoAccount(accDAO));
			cust.setAccounts(accounts);
			
		} catch (DataAccessException ex) {
			throw new Exception(ex);
		}
		return cust;
	}
	
	
	@Override
	public Customer updateAccountById(Long customerid,long accountid, Account account) throws Exception {
		// Need to validate exiting accounts
		Customer customer = validateAndGetExistingCustomerbyId(customerid);
		AccountDAO accountDAO = validateandGetExistingAccounDAOById(accountid);
		Set<Account> accounts = new HashSet<>();
		
		
		try {
			if(accountDAO.getPrimarycustomer().getId() != customerid)
				throw new InvalidArgumentException("This customer id " + customerid + " does not won account id  " + accountid);
			accountDAO.setAccountype(account.getAccounType());
			accountDAO.setAccountholdingtype(account.getAccountHoldingType());
			accountRepo.save(accountDAO);
			accounts.add(Utility.convertAccountDAOtoAccount(accountDAO));
			customer.setAccounts(accounts);
		} catch (DataAccessException ex) {
			throw new Exception(ex);
		}
		return customer;
	}

	
	
	private AccountDAO validateAndGetExistingAccountDAObyAccountNumber(long accountNumber) throws Exception {
		
		AccountDAO accountDAO = null;
		try {
			accountDAO = accountRepo.findByAccountnumber(accountNumber);
			if(accountDAO == null)
				throw new ResourceNotFoundException("Given Account number does not exits " +accountNumber);
		} catch (DataAccessException ex) {
			throw new Exception(ex);
		}
		
		return accountDAO;
		
	}

	private long get9digitAccountNumber() {
		return 100000000 + new Random().nextInt(900000000);
	}
	
	
	private Customer validateAndGetExistingCustomerbyId(long customerId) throws Exception
	{
		if (customerId == 0)
			throw new BadRequestException("customerId id cannot be 0 or less" + customerId);
		return getCustomerById(customerId);
	}
	
	private AccountDAO validateandGetExistingAccounDAOById(long accountId) throws Exception {
		Optional<AccountDAO> accountOp;
		try {
			accountOp = accountRepo.findById(accountId);
			if (accountOp.isEmpty()) {
				throw new ResourceNotFoundException("No account id exists for id " + accountId);

			}
		} catch (DataAccessException ex) {
			throw new Exception(ex);
		}
		return accountOp.get();
	}
	
	

	/*
	 * @Override public long updateAccount(Customer customer) throws Exception { //
	 * Need to validate exiting accounts
	 * validateAndGetExistingCustomerbyId(customer.getId()); Set<Account> accounts =
	 * customer.getAccounts(); if (accounts == null)// No account details from
	 * Restclient side throw new BadRequestException("No account data sent"); // Now
	 * get this customer's details from DB and match account try { CustomerDAO
	 * custDAO = customerRepo.findById(customer.getId()).get(); Set<AccountDAO>
	 * accDAOs =
	 * Utility.convertListToSet(accountRepo.findByPrimarycustomer(custDAO)); if
	 * (accDAOs.isEmpty()) // Ensure DB customers has account throw new
	 * InvalidArgumentException("Customer has no account in DB id " +
	 * customer.getId()); boolean match = false; for (Account acc : accounts) { for
	 * (AccountDAO accDAO : accDAOs) { if ((accDAO.getId() == acc.getId()) &&
	 * (acc.getAccountNumber() == accDAO.getAccountnumber())) { // Only account type
	 * and holding type can be changed here match = true;
	 * accDAO.setAccountype(acc.getAccounType());
	 * accDAO.setAccountholdingtype(acc.getAccountHoldingType());
	 * accountRepo.save(accDAO); }
	 * 
	 * } } if (!match)// so customer id is valid but accounts don't match bad data
	 * throw new InvalidArgumentException("Account Data mismatch" +
	 * customer.getId()); } catch (DataAccessException ex) { throw new
	 * Exception(ex); } return customer.getId(); }
	 */

	@Override
	public Customer transferBalance(BalanceTransfer transfer) throws Exception {
		Customer cust = validateAndGetExistingCustomerbyId(transfer.getCutsomerid());
		if (transfer.getTransferAmount() < 0)
			throw new InvalidArgumentException("Transfer amount cannot be negative");
		Set<Account> accounts = new HashSet<>(); 
		try {
			AccountDAO srcDAO = validateandGetExistingAccounDAOById(transfer.getSrcAccountid());
			AccountDAO destDAO = validateandGetExistingAccounDAOById(transfer.getDestAccountid());
			//OK Account Ids exists not to check if there are woned by the customer
			if((srcDAO.getPrimarycustomer().getId() != transfer.getCutsomerid())|| 
					(destDAO.getPrimarycustomer().getId() != transfer.getCutsomerid()))
				throw new InvalidArgumentException("Src Account ID " + srcDAO.getId() 
				+ " Dest Account ID " + destDAO.getId() + " is not both owned by Customer " + transfer.getCutsomerid());
			
			//OK all checked done
			double remainingAmount = srcDAO.getBalance() - transfer.getTransferAmount();
			if (remainingAmount >= 0) {
				// Ok go ahead with transfer
				double destBalance = destDAO.getBalance() + srcDAO.getBalance();
				srcDAO.setBalance(remainingAmount);
				destDAO.setBalance(destBalance);
				accountRepo.save(destDAO); // save dest first
				accountRepo.save(srcDAO); // save src next
				//add data for returning response
				accounts.add(Utility.convertAccountDAOtoAccount(srcDAO));
				accounts.add(Utility.convertAccountDAOtoAccount(destDAO));
			} else {
				throw new InvalidArgumentException("Not enough balance to transfer Current balance "
						+ srcDAO.getBalance() + " Transfer Amount " +transfer.getTransferAmount());
			}
		} catch (DataAccessException ex) {
			throw new Exception(ex);
		}
		cust.setAccounts(accounts);
		return cust;
	}

}
