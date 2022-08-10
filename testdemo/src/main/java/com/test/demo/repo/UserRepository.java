package com.test.demo.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.test.demo.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
	
}