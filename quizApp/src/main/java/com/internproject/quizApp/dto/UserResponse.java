package com.internproject.quizApp.dto;

public class UserResponse {
    private Long id;
    private String username;
    private String fname;
    private String lname;
    private String email;
    private String role;

    public UserResponse(Long id, String username, String fname, String lname, String email, String role) {
        this.id = id;
        this.username = username;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.role = role;
    }

}
