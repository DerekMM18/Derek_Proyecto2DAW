package com.example.ecosecha.service;

import com.example.ecosecha.model.User;

import java.util.Optional;

public interface UserDaoService {
    Optional<User> selectUserByUsername(String username);
}
