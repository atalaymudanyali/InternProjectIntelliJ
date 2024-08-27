package com.internproject.quizApp.repo;


import com.internproject.quizApp.model.User;
import com.internproject.quizApp.model.exam.Faculty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface userRepository extends JpaRepository<User,Long> {


    User findByuserName(String userName);
    List<User> findByFaculty(Faculty faculty);
}