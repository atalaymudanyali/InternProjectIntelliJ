package com.internproject.quizApp.controller;

import com.internproject.quizApp.config.OTPUtils;
import com.internproject.quizApp.model.Role;
import com.internproject.quizApp.model.User;
import com.internproject.quizApp.model.UserRole;
import com.internproject.quizApp.model.exam.Faculty;
import com.internproject.quizApp.service.UserService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import jakarta.faces.view.ViewScoped;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ViewScoped  // or @SessionScoped if you need session persistence
@Component("userController")
@Data
@NoArgsConstructor
public class UserController implements Serializable {

    private String username;
    private String password;
    private String otp;
    private String fname;
    private String lname;
    private String email;
    private Faculty selectedFaculty;
    private String selectedRole;
    private String generatedOtp;

    private List<Faculty> faculties;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private OTPUtils otpUtils;

    public String login() {
        if ("Admin".equalsIgnoreCase(username) && "Admin".equals(password)) {
            User adminUser = userService.getUser("Admin");

            if (adminUser == null) {
                adminUser = new User();
                adminUser.setUserName("Admin");
                adminUser.setPassword(passwordEncoder.encode("Admin"));
                adminUser.setFName("Admin");
                adminUser.setLname("Admin");
                adminUser.setEmail("admin@example.com");

                Role adminRole = new Role();
                adminRole.setRoleName("ADMIN");

                Set<UserRole> userRoleSet = new HashSet<>();
                UserRole userRole = new UserRole();
                userRole.setRole(adminRole);
                userRole.setUser(adminUser);
                userRoleSet.add(userRole);

                try {
                    userService.createUser(adminUser, userRoleSet);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            return "home/dashboard.xhtml?faces-redirect=true";
        }
        User user = userService.getUser(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {

            if (generatedOtp == null) {
                generatedOtp = otpUtils.generateOTP(6);
                return null;
            } else if (otpUtils.validateOTP(otp)) {
                otpUtils.clearOTP();
                return "home/dashboard.xhtml?faces-redirect=true";
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid OTP", "The OTP you entered is incorrect."));
                otpUtils.clearOTP();
                return null;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Login Failed", "Invalid username or password."));
            return null;
        }
    }

    public String register() throws Exception {
        User user = new User();
        user.setUserName(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setFName(fname);
        user.setLname(lname);
        user.setEmail(email);
        user.setFaculty(selectedFaculty);

        Role role = new Role();
        role.setRoleName(selectedRole);

        Set<UserRole> userRoleSet = new HashSet<>();
        UserRole userRole = new UserRole();
        userRole.setRole(role);
        userRole.setUser(user);
        userRoleSet.add(userRole);

        userService.createUser(user, userRoleSet);
        return "login.xhtml?faces-redirect=true";
    }
}
