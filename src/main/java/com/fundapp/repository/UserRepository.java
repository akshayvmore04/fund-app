package com.fundapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fundapp.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // Why findByPhone? -> Because login will be by phone number
    User findByPhone(String phone);
}
