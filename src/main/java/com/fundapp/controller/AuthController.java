package com.fundapp.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fundapp.dto.ApiResponse;
import com.fundapp.dto.LoginRequest;
import com.fundapp.dto.UserProfileResponse;
import com.fundapp.entity.User;
import com.fundapp.repository.UserRepository;
import com.fundapp.security.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        System.out.println("NAME = " + user.getName());
        System.out.println("PHONE = " + user.getPhone());
        System.out.println("RAW PASSWORD = " + user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER");
        user.setCreatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    @PostMapping("/login")
    public Map<String, String> login(
            @RequestBody LoginRequest request) {

        System.out.println("LOGIN PHONE = " + request.getPhone());

        System.out.println("LOGIN PASSWORD = " + request.getPassword());

        User user = userRepository
                .findByPhone(request.getPhone())
                .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println("DB PASSWORD = " + user.getPassword());

        boolean matches = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword());

        System.out.println("PASSWORD MATCH = " + matches);

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getPhone(),
                        request.getPassword()));

        String token = jwtUtil.generateToken(request.getPhone());

        return Map.of("token", token);
    }

    @GetMapping("/me")
    public ApiResponse<UserProfileResponse> getCurrentUser() {

        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        System.out.println("AUTH = " + auth);

        String phone = auth.getName();

        System.out.println("PHONE = " + phone);

        User user = userRepository
                .findByPhone(phone)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserProfileResponse response = new UserProfileResponse(
                user.getName(),
                user.getPhone(),
                user.getRole());

        return new ApiResponse<>(
                true,
                "User profile fetched successfully",
                response);
    }
    // @PostMapping("/login")
    // public String login(@RequestBody LoginRequest request) {

    // User user = userRepository.findByPhone(request.getPhone())
    // .orElseThrow(() -> new RuntimeException("User not found"));
    // if (user == null) {
    // return "User not found";
    // }

    // if (!user.getPassword().equals(request.getPassword())) {
    // return "Invalid Password";
    // }
    // return "Login successful";
    // }
}
