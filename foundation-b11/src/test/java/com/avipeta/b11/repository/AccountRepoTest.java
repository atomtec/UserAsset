package com.avipeta.b11.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.avipeta.b11.entity.AccountDAO;
import com.avipeta.b11.entity.CustomerDAO;
import com.avipeta.b11.model.AccountHoldingType;


@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class AccountRepoTest {
	
	@Autowired
    AccountRepo repository;
	
	@Autowired
    CustomerRepo customerRepo;
	
	
	@Test
    @Transactional
    @Sql(scripts = { "/account-data.sql" })
    public void testFindAll() 
    {
        List<AccountDAO> acclist = repository.findAll();     
        Assertions.assertTrue(acclist.size() == 3);
    }
	
	
	@Test
    @Transactional
    @Sql(scripts = { "/test-added-customers.sql" })
    public void testAddAccount() 
    {
		Optional<CustomerDAO> cust = customerRepo.findById(2L);
    	CustomerDAO test = cust.get();
    	AccountDAO accDAO = new AccountDAO();
    	accDAO.setAccountholdingtype(AccountHoldingType.CORPORATE);
    	accDAO.setAccountnumber(999999990L);
    	accDAO.setBalance(100.0);
    	accDAO.setPrimarycustomer(test);
    	repository.save(accDAO);
    	cust = customerRepo.findById(2L);
    	Set<AccountDAO> accDAOS = cust.get().getAccounts();
    	for (AccountDAO accDao: accDAOS)
    	{
    		Assertions.assertTrue(accDao.getBalance() == 100.0);
    	}
    	
    }
	
	@Test
    @Transactional
    @Sql(scripts = { "/account-data.sql" })
    public void testfindByPrimarycustomer() 
    {
		Optional<CustomerDAO> cust = customerRepo.findById(1L);
    	CustomerDAO test = cust.get();
        List<AccountDAO> accountList = repository.findByPrimarycustomer(test);     
        for (AccountDAO acc : accountList) {
       	 Assertions.assertTrue(acc.getPrimarycustomer().getId() == 1L);
       }

    }
	
	@Test
    @Transactional
    @Sql(scripts = { "/account-data.sql" })
    public void testfindByAccountnumberw() 
    {
        AccountDAO account = repository.findByAccountnumber(869773056L);     
        Assertions.assertTrue(account.getAccountnumber() == 869773056L);
    }
	
	@Test
    @Transactional
    @Sql(scripts = { "/account-data.sql" })
    public void testdeleteById() 
    {
    	repository.deleteById((long) 1);
    	List<AccountDAO> acclist = repository.findAll();   
        Assertions.assertTrue(acclist.size() == 2);
        for (AccountDAO acc : acclist) {
        	 Assertions.assertTrue(acc.getId() != 1);
        }
    }
	 
	@Test
    @Transactional
    @Sql(scripts = { "/account-data.sql" })
    public void testUpdate() 
    {
		 AccountDAO account = repository.findByAccountnumber(869773056L);     
	     Assertions.assertTrue(account.getAccountnumber() == 869773056L);
	     Assertions.assertTrue(account.getBalance() == 900.0);
	     account.setBalance(1000.0);
	     repository.save(account);
	     account = repository.findByAccountnumber(869773056L);  
	     Assertions.assertTrue(account.getBalance() == 1000.0);

    }
	
	@Test
    @Transactional
    @Sql(scripts = { "/account-data.sql" })
    public void testDeleteCustomerdeletesAccounts() 
    {
    	CustomerDAO cust = customerRepo.findByEmailid("rama@gmail.com");
        customerRepo.delete(cust);
        List<AccountDAO> acclist = repository.findAll();   
        Assertions.assertTrue(acclist.size() == 2);
        for (AccountDAO acc : acclist) {
        	 Assertions.assertTrue(acc.getId() != 3);
        }
        
    }

}
