package com.avipeta.b11.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.avipeta.b11.entity.AccountDAO;
import com.avipeta.b11.entity.CustomerDAO;

public interface AccountRepo extends JpaRepository<AccountDAO, Long> {
	List<AccountDAO> findByPrimarycustomer(CustomerDAO primarycustomer);
	AccountDAO findByAccountnumber(Long accountnumber);
	
}
