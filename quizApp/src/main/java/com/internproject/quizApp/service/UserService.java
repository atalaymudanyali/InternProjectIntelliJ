package com.internproject.quizApp.service;

import java.util.Set;

import com.internproject.quizApp.model.User;
import com.internproject.quizApp.model.UserRole;

public interface UserService {
    public User createUser(User user, Set<UserRole> userRoles) throws Exception;
    public User getUser(String uname);
    public void deleteUser(Long userId);
    void updatePassword(String username, String newPassword) throws Exception;

}