package com.internproject.quizApp.controller;

import com.internproject.quizApp.config.OTPUtils;
import com.internproject.quizApp.service.UserService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component("forgetPasswordController")
@ViewScoped
public class ForgetPasswordController implements Serializable {

    @Autowired
    private UserService userService;

    @Autowired
    private OTPUtils otpUtils;

    @Getter @Setter
    private String username;

    @Getter @Setter
    private String newPassword;

    @Getter @Setter
    private String otp;  // Changed from inputOtp to otp

    @Getter
    private boolean otpVerified = false;

    private String generatedOtp;

    public void generateOtp() {
        generatedOtp = otpUtils.generateOTP(4); // Generate a 4-digit OTP
        // Display the OTP on the screen
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, "OTP Generated", "Your OTP is: " + generatedOtp));
    }

    public String verifyOtp() {  // Changed from verifyOtpAndUpdatePassword to verifyOtp
        if (generatedOtp != null && generatedOtp.equals(otp)) {
            try {
                userService.updatePassword(username, newPassword); // Update password in UserService
                otpVerified = true;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Password Reset", "Your password has been reset successfully."));
                return "login.xhtml?faces-redirect=true"; // Redirect to login page
            } catch (Exception e) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Could not reset the password: " + e.getMessage()));
            }
        } else {
            // OTP is invalid
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid OTP", "The OTP you entered is incorrect."));
        }
        return null;
    }
}
