package com.example.demo.auth.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.auth.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
}