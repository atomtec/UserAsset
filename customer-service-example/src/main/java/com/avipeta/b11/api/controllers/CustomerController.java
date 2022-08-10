package com.avipeta.b11.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.avipeta.b11.Utility;
import com.avipeta.b11.model.Account;
import com.avipeta.b11.model.BalanceTransfer;
import com.avipeta.b11.model.Customer;
import com.avipeta.b11.service.CustomerService;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/")
    public String index()  {
        return "Hello World!";
    }
	
	@GetMapping("/testcreateuser")
	public Customer testcreateuser() throws Exception  {
		  Customer cust = customerService.create(Utility.getCustomer());
		  return customerService.addAccountById(cust.getId(), Utility.getAccount());
    }
	
	@GetMapping("/customers")
    public List<Customer> getAllCustomers() throws Exception {
        return customerService.getAllCustomers();
    }
	
	@GetMapping("/customer/{id}")
    public Customer getCustomerById(@PathVariable(value = "id") Long customerid) throws Exception {
        return customerService.getCustomerById(customerid);
    }
	
	@PostMapping("/customer")
    public Customer createCustomer(@Valid @RequestBody Customer customer) throws Exception {
        return customerService.create(customer);
    }
	
	@PutMapping("/customer/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable(value = "id") Long customerid,
    		@Valid @RequestBody Customer customer) throws Exception {
        return ResponseEntity.ok(customerService.update(customer,customerid));
    }
	
	@DeleteMapping("/customer/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable(value = "id") Long customerid) throws Exception {
        return ResponseEntity.ok(customerService.deleteCustomerById(customerid));
    }
	
	@PostMapping("/customer/{id}/account")
    public Customer createCustomerAccount(@PathVariable(value = "id") Long customerid,
    		@Valid @RequestBody Account account) throws Exception {
        return customerService.addAccountById(customerid, account);
    }
	
	@PutMapping("/customer/{id}/account/{accountid}")
    public ResponseEntity<Customer> updateCustomerAccount(@PathVariable(value = "id") Long customerid,
    		@PathVariable(value = "accountid") Long accountid,@Valid @RequestBody Account account) throws Exception {
        return ResponseEntity.ok(customerService.updateAccountById(customerid, accountid, account));
    }
	
	@PatchMapping("/customer/{id}/account/{accountno}/balance")
    public ResponseEntity<Customer> updateCustomerAccountBalanceByAccountNo(@PathVariable(value = "id") Long customerid,
    		@PathVariable(value = "accountno") Long accountnumber,@Valid @RequestBody double fundAmount) throws Exception {
        return ResponseEntity.ok(customerService.updateAccountBalanceByAccNo(customerid, accountnumber, fundAmount));
    }
	
	@PatchMapping("/customer/{id}/account/transfer")
    public ResponseEntity<Customer> updateCustomerAccountBalanceByAccountNo(@PathVariable(value = "id") Long customerid,
    		@Valid @RequestBody BalanceTransfer balancetransfer) throws Exception {
        return ResponseEntity.ok(customerService.transferBalance(balancetransfer));
    }
	
	

}
