package com.avipeta.b11.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avipeta.b11.entity.CustomerDAO;


public interface CustomerRepo extends JpaRepository<CustomerDAO, Long> {
	List<CustomerDAO> findByFirstname(String firstname);
	List<CustomerDAO> findByLastname(String lastname);
	CustomerDAO findByEmailid(String emailid);
	CustomerDAO findByPannumber(String pannumber);
	CustomerDAO findByPhonenumber(String phonenumber);
	List<CustomerDAO> findByAddress(String address);
}