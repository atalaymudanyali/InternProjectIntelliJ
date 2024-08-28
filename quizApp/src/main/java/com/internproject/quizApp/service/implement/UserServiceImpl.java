package com.internproject.quizApp.service.implement;

import com.internproject.quizApp.dto.LoginRequest;
import com.internproject.quizApp.dto.ResponseObject;
import com.internproject.quizApp.dto.UserRegistrationRequest;
import com.internproject.quizApp.dto.UserResponse;
import com.internproject.quizApp.exception.ValidationException;
import com.internproject.quizApp.model.User;
import com.internproject.quizApp.model.UserRole;
import com.internproject.quizApp.repo.userRepository;
import com.internproject.quizApp.service.UserService;
import com.internproject.quizApp.validation.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private userRepository userRepository;

    @Autowired
    private ValidationService validationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseObject loginUser(LoginRequest loginRequest) {
        if (!validationService.validateLoginRequest(loginRequest.getUsername(), loginRequest.getPassword())) {
            throw new ValidationException("Invalid username or password format");
        }

        User user = userRepository.findByuserName(loginRequest.getUsername());
        if (user != null && passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            UserResponse userResponse = new UserResponse(user.getId(), user.getUserName(), user.getFName(), user.getLname(), user.getEmail(), user.getRole().name());
            return new ResponseObject(HttpStatus.OK, "Login successful", userResponse);
        }
        return new ResponseObject(HttpStatus.UNAUTHORIZED, "Invalid username or password");
    }

    @Override
    public ResponseObject registerUser(UserRegistrationRequest request) {
        if (!validationService.isValidUserName(request.getUsername())) {
            return new ResponseObject(HttpStatus.BAD_REQUEST, "Invalid username");
        }
        if (!validationService.isValidEmail(request.getEmail())) {
            return new ResponseObject(HttpStatus.BAD_REQUEST, "Invalid email");
        }

        User user = new User();
        user.setUserName(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFName(request.getFname());
        user.setLname(request.getLname());
        user.setEmail(request.getEmail());
        user.setRole(UserRole.valueOf(request.getRole().toUpperCase()));

        userRepository.save(user);
        return new ResponseObject(HttpStatus.OK, "User registered successfully");
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUser(String uname) {
        return userRepository.findByuserName(uname);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void updatePassword(String username, String newPassword) throws Exception {
        User user = userRepository.findByuserName(username);
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
        } else {
            throw new Exception("User not found");
        }
    }
}
