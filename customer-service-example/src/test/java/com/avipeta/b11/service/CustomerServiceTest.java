package com.avipeta.b11.service;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import com.avipeta.b11.model.Account;
import com.avipeta.b11.model.AccountHoldingType;
import com.avipeta.b11.model.AccountType;
import com.avipeta.b11.model.BalanceTransfer;
import com.avipeta.b11.model.Customer;

@AutoConfigureTestDatabase(replace=Replace.NONE)
@SpringBootTest
public class CustomerServiceTest {
	
	@Autowired
	CustomerService customerService;
	
	@Test
    @Transactional
	public void testCreate() throws Exception {
		Assertions.assertNotNull(customerService.create(getCustomer()));
	}
	
	@Test
    @Transactional
	public void testUpdate() throws Exception {
		Customer cust = customerService.create(getCustomer());
		cust = customerService.getCustomerById(cust.getId());
		Assertions.assertNotNull(cust);
		cust.setLastName("Hello");
		customerService.update(cust,cust.getId());
		cust = customerService.getCustomerById(cust.getId());
		Assertions.assertTrue(cust.getLastName().equals("Hello"));
		
		
	}
	
	@Test
    @Transactional
    @Sql(scripts = { "/test-added-customers.sql" })
    public void testgetCustomerById() throws Exception 
    {
		Customer customer = customerService.getCustomerById(1L);
		Assertions.assertTrue(customer.getId() == 1L);
    }
	
	
	/*
	 * @Test
	 * 
	 * @Transactional
	 * 
	 * @Sql(scripts = { "/test-added-customers.sql" }) public void testaddAccount()
	 * throws Exception { Customer customer = customerService.getCustomerById(2L);
	 * Assertions.assertTrue("Accout is empty",customer.getAccounts().isEmpty());
	 * Account account = new Account();
	 * account.setAccountHoldingType(AccountHoldingType.INDIVIDUAL);
	 * account.setAccounType(AccountType.SAVINGS); account.setBalance(100.0);
	 * Set<Account> accSet = new HashSet<>(); accSet.add(account);
	 * customer.setAccounts(accSet);
	 * Assertions.assertNotNull(customerService.addAccount(customer)); customer =
	 * customerService.getCustomerById(2L); Assertions.assertNotNull(customer);
	 * Assertions.assertTrue("Accout has size 1",customer.getAccounts().size() == 1);
	 * for (Account acc : customer.getAccounts()) {
	 * Assertions.assertTrue("Balance must match",acc.getBalance() == 100.0); } }
	 */
	
	
	@Test
    @Transactional
    @Sql(scripts = { "/test-added-customers.sql" })
    public void testaddAccountById() throws Exception 
    {
		Customer customer = customerService.getCustomerById(2L);
		Assertions.assertTrue(customer.getAccounts().isEmpty());
		Account account = new Account();
		account.setAccountHoldingType(AccountHoldingType.INDIVIDUAL);
		account.setAccounType(AccountType.SAVINGS);
		account.setBalance(100.0);
		customerService.addAccountById(customer.getId(), account);
		customer = customerService.getCustomerById(2L);
		Assertions.assertTrue(customer.getAccounts().size() == 1);
		for (Account acc : customer.getAccounts()) {
			Assertions.assertTrue(acc.getBalance() == 100.0);
	    }
    }
	
	/*
	 * @Test
	 * 
	 * @Transactional
	 * 
	 * @Sql(scripts = { "/account-data.sql" }) public void testUpdateAccount()
	 * throws Exception { Customer customer = customerService.getCustomerById(4L);
	 * Set<Account> accounts = customer.getAccounts(); for (Account accs: accounts)
	 * { Assertions.assertTrue("Accout type is savings",
	 * accs.getAccounType().equals(AccountType.SAVINGS));
	 * Assertions.assertTrue("Accout holdtype is individual",
	 * accs.getAccountHoldingType().equals(AccountHoldingType.INDIVIDUAL)); //Now
	 * change accs.setAccountHoldingType(AccountHoldingType.CORPORATE);
	 * accs.setAccounType(AccountType.CURRENT); }
	 * 
	 * //Update customerService.updateAccount(customer);
	 * 
	 * //Fetch and check if updated
	 * 
	 * customer = customerService.getCustomerById(4L); accounts =
	 * customer.getAccounts();
	 * 
	 * for (Account accs: accounts) {
	 * Assertions.assertTrue("Accout type is CURRENT",accs.getAccounType().equals(
	 * AccountType.CURRENT)); Assertions.assertTrue("Accout holdtype is CORPORATE",
	 * accs.getAccountHoldingType().equals(AccountHoldingType.CORPORATE)); } }
	 */
	
	
	@Test
    @Transactional
    @Sql(scripts = { "/account-data.sql" })
    public void testUpdateAccountById() throws Exception 
    {
		Customer customer = customerService.getCustomerById(4L);
		Set<Account> accounts = customer.getAccounts();
		for (Account accs: accounts) {
			Assertions.assertTrue(accs.getAccounType().equals(AccountType.SAVINGS));
			Assertions.assertTrue(accs.getAccountHoldingType().equals(AccountHoldingType.INDIVIDUAL));
			//Now change 
			accs.setAccountHoldingType(AccountHoldingType.CORPORATE);
			accs.setAccounType(AccountType.CURRENT);
			//Update 
			customerService.updateAccountById(customer.getId(),accs.getId(),accs);
		}
		
		
		
		//Fetch and check if updated 
		
		customer = customerService.getCustomerById(4L);
		accounts = customer.getAccounts();
		
		for (Account accs: accounts) {
			Assertions.assertTrue(accs.getAccounType().equals(AccountType.CURRENT));
			Assertions.assertTrue(accs.getAccountHoldingType().equals(AccountHoldingType.CORPORATE));
		}
    }
	
	
	
	@Test
    @Transactional
    @Sql(scripts = { "/account-data.sql" })
    public void testUpdateBalanceByAccountNumber() throws Exception 
    {
		Customer customer = customerService.getCustomerById(4L);
		Set<Account> accounts = customer.getAccounts();
		for (Account accs: accounts) {
			customerService.updateAccountBalanceByAccNo(customer.getId(), accs.getAccountNumber(), -50.0);
		}
		
		//Fetch and check if updated 
		
		customer = customerService.getCustomerById(4L);
		accounts = customer.getAccounts();
		
		for (Account acc : customer.getAccounts()) {
			Assertions.assertTrue(acc.getBalance() == 850.0);
	    }
    }
	
	
	@Test
    @Transactional
    @Sql(scripts = { "/account-data.sql" }) 
    public void testBalanceTransfer() throws Exception 
    {
		Customer customer = customerService.getCustomerById(1L);
		BalanceTransfer transfer = new BalanceTransfer();
		transfer.setCutsomerid(customer.getId());
		transfer.setSrcAccountid(1L);
		transfer.setDestAccountid(2L);
		transfer.setTransferAmount(100.0);
		customerService.transferBalance(transfer);
		customer = customerService.getCustomerById(1L);
		Set<Account> accounts = customer.getAccounts();
		for (Account accs: accounts) {
			if(accs.getId() == 1L) {
			 Assertions.assertTrue(accs.getBalance() == 0.0);
			}
			else if(accs.getId() == 2L) {
				 Assertions.assertTrue(accs.getBalance() == 300.0);
				}
			
		}
    }
	
	
	@Test
    @Transactional
    @Sql(scripts = { "/test-added-customers.sql" })
    public void testgetAllCustomers() throws Exception 
    {
		List<Customer> customers = customerService.getAllCustomers();
		Assertions.assertTrue(customers.size() == 4);
    }
	
	@Test
    @Transactional
    @Sql(scripts = { "/test-added-customers.sql" })
    public void testdeleteAllCustomers() throws Exception 
    {
        customerService.deleteAllCustomers();
        List<Customer> customers = customerService.getAllCustomers();
		Assertions.assertTrue(customers.size() == 0);
    }
	
	
	@Test
    @Transactional
    @Sql(scripts = { "/test-added-customers.sql" })
    public void testdeleteCustomerById() throws Exception 
    {
        customerService.deleteCustomerById(1L);
        List<Customer> customers = customerService.getAllCustomers();
        for (Customer cust : customers) {
       	 Assertions.assertTrue(cust.getId() != 1);
       }
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

}
