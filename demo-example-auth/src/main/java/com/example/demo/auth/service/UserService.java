package com.example.demo.auth.service;

import com.example.demo.auth.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}