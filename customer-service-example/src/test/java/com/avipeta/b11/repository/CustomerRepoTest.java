package com.avipeta.b11.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import com.avipeta.b11.entity.CustomerDAO;


@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class CustomerRepoTest {
	
	@Autowired
    CustomerRepo repository;
     
    @Test
    @Transactional
    public void testcreateCustomer() 
    {
        CustomerDAO cust = new CustomerDAO();
        cust.setPhonenumber("9831645612");
        cust.setLastname("Gupta");
        cust.setEmailid("howtodoinjava@gmail.com");
        cust.setAddress("Gope Lane");
        cust.setFirstname("Lokesh");
        cust.setPannumber("BCMPK");
         
        repository.save(cust);
         
        Assertions.assertNotNull(cust.getId());
    }
    
    @Test
    @Transactional
    @Sql(scripts = { "/test-added-customers.sql" })
    public void testFindAll() 
    {
        List<CustomerDAO> custList = repository.findAll();     
        Assertions.assertTrue(custList.size() == 4);
    }
    
    @Test
    @Transactional
    @Sql(scripts = { "/test-added-customers.sql" })
    public void testdeleteById() 
    {
    	repository.deleteById((long) 1);
        List<CustomerDAO> custList = repository.findAll();     
        Assertions.assertTrue(custList.size() == 3);
        for (CustomerDAO cust : custList) {
        	 Assertions.assertTrue(cust.getId() != 1);
        }
    }
    
    @Test
    @Transactional
    @Sql(scripts = { "/test-added-customers.sql" })
    public void testfindByFirstName() 
    {
        List<CustomerDAO> custList = repository.findByFirstname("Rama");
        Assertions.assertTrue(custList.size() == 1);
        for (CustomerDAO cust : custList) {
        	 Assertions.assertTrue(cust.getFirstname().equals("Rama"));
        }
    }
    
    @Test
    @Transactional
    @Sql(scripts = { "/test-added-customers.sql" })
    public void testfindByLastName() 
    {
        List<CustomerDAO> custList = repository.findByLastname("Kumar");
        Assertions.assertTrue(custList.size() == 2);
        for (CustomerDAO cust : custList) {
        	 Assertions.assertTrue(cust.getLastname().equals("Kumar"));
        }
    }
    
    @Test
    @Transactional
    @Sql(scripts = { "/test-added-customers.sql" })
    public void testfindByPhoneNumber() 
    {
        CustomerDAO cust = repository.findByPhonenumber("234562");
        Assertions.assertTrue(cust.getPhonenumber().equals("234562"));
    }
    
    @Test
    @Transactional
    @Sql(scripts = { "/test-added-customers.sql" })
    public void testfindByPanNumber() 
    {
        CustomerDAO cust = repository.findByPannumber("ABCDEF4");
        Assertions.assertTrue(cust.getPannumber().equals("ABCDEF4"));
    }
    
    @Test
    @Transactional
    @Sql(scripts = { "/test-added-customers.sql" })
    public void testfindByEmailId() 
    {
        CustomerDAO cust = repository.findByEmailid("rama@gmail.com");
        Assertions.assertTrue(cust.getEmailid().equals("rama@gmail.com"));
    }
    
    @Test
    @Transactional
    @Sql(scripts = { "/test-added-customers.sql" })
    public void testfindByAddress() 
    {
        List<CustomerDAO> custList = repository.findByAddress("Prop Lane");
        Assertions.assertTrue(custList.size() == 4);
        for (CustomerDAO cust : custList) {
        	 Assertions.assertTrue(cust.getAddress().equals("Prop Lane"));
        }
    }
    
    
    
    
    @Test
    @Transactional
    @Sql(scripts = { "/test-added-customers.sql" })
    public void testUpdate()
    {
    	 Optional<CustomerDAO> cust = repository.findById(1L);
    	 CustomerDAO test = cust.get();
         Assertions.assertTrue(test.getPannumber().equals("ABCDEF2"));
         test.setPannumber("BCMPK11");
         repository.save(test);
         cust = repository.findById(1L);
    	 test = cust.get();
         Assertions.assertTrue(test.getPannumber().equals("BCMPK11"));
         
    }
    
    

}
