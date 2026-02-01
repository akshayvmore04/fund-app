package com.fundapp.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;   // 👈 MUST BE THIS
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fundapp.entity.User;
import com.fundapp.repository.UserRepository;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public User register(@RequestBody User user) {   // 👈 MUST BE HERE
        System.out.println("NAME = " + user.getName());
        System.out.println("PHONE = " + user.getPhone());
        System.out.println("PASSWORD = " + user.getPassword());

        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }
}
