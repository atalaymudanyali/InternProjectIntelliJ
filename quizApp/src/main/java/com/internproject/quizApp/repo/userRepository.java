package com.internproject.quizApp.repo;


import com.internproject.quizApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface userRepository extends JpaRepository<User,Long> {


    User findByuserName(String userName);
}