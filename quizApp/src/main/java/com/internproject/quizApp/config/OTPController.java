package com.internproject.quizApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class OTPController {

    @Autowired
    private OTPUtils otpUtils;

    @PostMapping("/generate-otp")
    public ResponseEntity<?> generateOtp(@RequestParam("length") int otpLength) {
        if (otpLength <= 0) {
            return ResponseEntity.badRequest().body("OTP length must be greater than 0");
        }
        String generatedOtp = otpUtils.generateOTP(otpLength);
        return ResponseEntity.ok("OTP generated: " + generatedOtp);
    }

    @PostMapping("/validate-otp")
    public ResponseEntity<?> validateOtp(@RequestParam("otp") String otp) {
        if (otpUtils.validateOTP(otp)) {
            return ResponseEntity.ok("OTP validated successfully");
        } else {
            return ResponseEntity.status(401).body("Invalid OTP");
        }
    }
}