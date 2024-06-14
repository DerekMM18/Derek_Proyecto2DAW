package com.example.ecosecha.repository;

import com.example.ecosecha.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
