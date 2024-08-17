package com.internproject.quizApp;

import jakarta.faces.context.FacesContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.internproject.quizApp.model.Role;
import com.internproject.quizApp.model.User;
import com.internproject.quizApp.model.UserRole;
import com.internproject.quizApp.repo.userRepository;
import com.internproject.quizApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class QuizAppApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(QuizAppApplication.class, args);
	}

	// @Autowired
	// private UserService userService;

	@Override
	public void run(String... args) throws Exception {

		System.out.println("Project Running");

		// Create a new user
		// User user = new User();
		// user.setFName("atalay");
		// user.setLname("mudanyali");
		// user.setEmail("atalay.mudanyali@gmail.com");
		// user.setPassword("12345");
		// user.setUserName("atalay");
		// user.setPhone("7906544082");
		// System.out.println("User Created!");
		String version = FacesContext.class.getPackage().getImplementationVersion();
		System.out.println(version);

		// Set a default profile picture (if necessary)
		// user.setProfile("default.png");

		// Create a new role
		//Role role = new Role();
		//role.setRoleId(44L);
		//role.setRoleName("ADMIN");

		// Associate the role with the user
		//Set<UserRole> userRoleSet = new HashSet<>();
		//UserRole userRole = new UserRole();
		//userRole.setRole(role);
		//userRole.setUser(user);
		//userRoleSet.add(userRole);

		// Save the user and roles using the UserService
		// User user1 = this.userService.createUser(user, userRoleSet);
		//System.out.println(user1);
	}
}