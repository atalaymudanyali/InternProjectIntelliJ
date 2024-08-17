package com.internproject.quizApp.config;

import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OTPUtils {

    @Getter
    private String generatedOtp;
    private long otpGenerationTime;
    private int otpLength;

    public String generateOTP(int length) {
        this.otpLength = length;
        Random random = new Random();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < otpLength; i++) {
            otp.append(random.nextInt(10));
        }

        generatedOtp = otp.toString();
        otpGenerationTime = System.currentTimeMillis();
        return generatedOtp;
    }

    public boolean validateOTP(String inputOtp) {
        long currentTime = System.currentTimeMillis();
        return inputOtp.equals(generatedOtp) && (currentTime - otpGenerationTime) <= TimeUnit.MINUTES.toMillis(1);
    }

    public void clearOTP() {
        generatedOtp = null;
        otpGenerationTime = 0;
    }

}
