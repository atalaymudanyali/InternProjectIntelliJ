package com.internproject.quizApp.validation;

import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ValidationService {

    private static final int MAX_LENGTH = 50;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    public boolean validateLoginRequest(String username, String password) {
        return isValidUserName(username) && isValidPassword(password);
    }

    public boolean validateRegistrationRequest(String username, String password, String fname, String lname, String email, String role) {
        return isValidUserName(username) &&
                isValidPassword(password) &&
                isValidName(fname) &&
                isValidName(lname) &&
                isValidEmail(email) &&
                isValidRole(role);
    }

    public boolean isValidUserName(String username) {
        return username != null && !username.isEmpty() && username.length() <= MAX_LENGTH;
    }

    public boolean isValidPassword(String password) {
        return password != null && !password.isEmpty();
    }

    public boolean isValidName(String name) {
        return name != null && !name.isEmpty() && name.length() <= MAX_LENGTH;
    }

    public boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public boolean isValidRole(String role) {
        return role != null && !role.isEmpty();
    }
}
