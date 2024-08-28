package com.internproject.quizApp.service;

import com.internproject.quizApp.dto.LoginRequest;
import com.internproject.quizApp.dto.ResponseObject;
import com.internproject.quizApp.dto.UserRegistrationRequest;
import com.internproject.quizApp.model.User;

public interface UserService {
    ResponseObject loginUser(LoginRequest loginRequest);

    ResponseObject registerUser(UserRegistrationRequest request);

    User createUser(User user);

    User getUser(String uname);

    void deleteUser(Long userId);

    void updatePassword(String username, String newPassword) throws Exception;
}
