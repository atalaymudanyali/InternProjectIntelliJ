package com.internproject.quizApp.controller;

import com.internproject.quizApp.dto.LoginRequest;
import com.internproject.quizApp.dto.ResponseObject;
import com.internproject.quizApp.dto.UserRegistrationRequest;
import com.internproject.quizApp.dto.UserResponse;
import com.internproject.quizApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(@RequestBody LoginRequest loginRequest) {
        ResponseObject response = userService.loginUser(loginRequest);
        return new ResponseEntity<>(response, response.getStatus());
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseObject> register(@RequestBody UserRegistrationRequest request) {
        ResponseObject response = userService.registerUser(request);
        return new ResponseEntity<>(response, response.getStatus());
    }
}
