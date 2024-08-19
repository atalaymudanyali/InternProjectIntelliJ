package com.internproject.quizApp.controller;

import com.internproject.quizApp.config.OTPUtils;
import com.internproject.quizApp.dto.LoginRequest;
import com.internproject.quizApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
public String otp;
    @Autowired
    private OTPUtils otpUtils;

    @Autowired
    private UserService userService;

    @PostMapping("/generate-otp")
    public ResponseEntity<String> generateOtp(@RequestBody String username) {
        // Generate OTP and send it (e.g., via email or SMS)
        otp = otpUtils.generateOTP(6);
        // userService.saveOtpForUser(username, otp); // Save OTP associated with the user

        return ResponseEntity.ok("OTP has been sent." + otp);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        // Here you would validate the user credentials and OTP
        // Example:
        if (otpUtils.validateOTP(otp)) {
            // Proceed with login (e.g., issue a JWT token)
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP");
        }
    }
}
