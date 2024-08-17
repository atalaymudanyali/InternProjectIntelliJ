package com.internproject.quizApp.service.implement;

import java.util.Optional;
import java.util.Set;

import com.internproject.quizApp.config.Base64PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.internproject.quizApp.model.User;
import com.internproject.quizApp.model.UserRole;
import com.internproject.quizApp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private com.internproject.quizApp.repo.userRepository userRepository;

    @Autowired
    private com.internproject.quizApp.repo.roleRepository roleRepository;
    @Autowired
    private Base64PasswordEncoder base64PasswordEncoder;

    @Override
    public User createUser(User user, Set<UserRole> userRoles) throws Exception {

        User local = this.userRepository.findByuserName(user.getUsername());

        if (local != null) {
            System.out.println("User already present");
            throw new Exception("User already present");
        } else {
            for (UserRole ur : userRoles) {
                roleRepository.save(ur.getRole());
            }
            user.getUserRoles().addAll(userRoles);
            local = this.userRepository.save(user);
        }
        return local;
    }

    @Override
    public User getUser(String uname) {
        return this.userRepository.findByuserName(uname);
    }

    @Override
    public void deleteUser(Long userId) {
        this.userRepository.deleteById(userId);
    }

    @Override
    public void updatePassword(String username, String newPassword) throws Exception {
        Optional<User> optionalUser = Optional.ofNullable(userRepository.findByuserName(username));

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String encodedPassword = base64PasswordEncoder.encode(newPassword);
            user.setPassword(encodedPassword);
            userRepository.save(user);
        } else {
            throw new Exception("User not found with username: " + username);
        }

    }
}