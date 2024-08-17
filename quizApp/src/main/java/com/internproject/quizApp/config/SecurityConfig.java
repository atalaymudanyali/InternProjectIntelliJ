package com.internproject.quizApp.config;

import com.internproject.quizApp.service.UserService;
import com.internproject.quizApp.service.implement.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.stream.Collectors;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login.xhtml","/register.xhtml","/home/dashboard.xhtml", "forgetPassword.xhtml","/auth/generate-otp", "/auth/validate-otp" , "/error").permitAll()
                        .requestMatchers("/resources/**", "/static/**", "/webjars/**").permitAll()  // Allow access to static resources
                        // .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/home/createQuestion.xhtml", "/home/createQuiz.xhtml").hasAnyRole("TEACHER", "ADMIN")
                        // .requestMatchers("/student/**").hasAnyRole("STUDENT", "ADMIN")
                        .anyRequest().authenticated()  // Require authentication for other requests
                )
                .formLogin(form -> form
                        .loginPage("/login.xhtml")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Base64PasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }



    // CHECK AGAIN
    @Bean
    public UserDetailsService userDetailsService(UserServiceImpl userServiceImpl) {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                com.internproject.quizApp.model.User user = userServiceImpl.getUser(username);
                if (user == null) {
                    throw new UsernameNotFoundException("User not found with username: " + username);
                }

                return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                        .password(user.getPassword()) // Assuming the password is already encoded
                        .authorities(user.getUserRoles().stream()
                                .map(role -> new SimpleGrantedAuthority(role.getRole().getRoleName()))
                                .collect(Collectors.toList()))
                        .accountExpired(false)
                        .accountLocked(false)
                        .credentialsExpired(false)
                        .disabled(false)
                        .build();
            }
        };
    }

}
