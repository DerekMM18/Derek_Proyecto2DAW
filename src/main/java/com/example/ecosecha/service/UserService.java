package com.example.ecosecha.service;

import com.example.ecosecha.model.User;

public interface UserService {
    String save(User user);
    User getByUsername(String username);
    boolean existsByUsername(String username);
}
