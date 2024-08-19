package com.internproject.quizApp.controller;

import com.internproject.quizApp.config.OTPUtils;
import com.internproject.quizApp.dto.UserRegistrationRequest;
import com.internproject.quizApp.model.Role;
import com.internproject.quizApp.model.User;
import com.internproject.quizApp.model.UserRole;
import com.internproject.quizApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private OTPUtils otpUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private String generatedOtp;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password, @RequestParam(required = false) String otp) {
        User user = userService.getUser(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {

            // If OTP is required and provided
            if (generatedOtp != null) {
                if (otp != null && otpUtils.validateOTP(otp)) {
                    otpUtils.clearOTP();
                    return ResponseEntity.ok("Login successful");
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
                }
            } else {
                // Generate OTP and return to user
                generatedOtp = otpUtils.generateOTP(6);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body("OTP generated. Please provide OTP to complete login.");
            }

        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRegistrationRequest request) {
        try {
            User user = new User();
            user.setUserName(request.getUsername());
            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setFName(request.getFname());
            user.setLname(request.getLname());
            user.setEmail(request.getEmail());

            Role role = new Role();
            role.setRoleName(request.getRole());
            Set<UserRole> userRoleSet = new HashSet<>();
            UserRole userRole = new UserRole();
            userRole.setRole(role);
            userRole.setUser(user);
            userRoleSet.add(userRole);

            userService.createUser(user, userRoleSet);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed: " + e.getMessage());
        }
    }
}
