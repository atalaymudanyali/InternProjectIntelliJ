package com.internproject.quizApp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationRequest {
    private String username;
    private String password;
    private String fname;
    private String lname;
    private String email;
    private String role;


}
