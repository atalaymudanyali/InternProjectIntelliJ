package com.internproject.quizApp.repo;


import com.internproject.quizApp.model.exam.Category;
import com.internproject.quizApp.model.exam.Faculty;
import com.internproject.quizApp.model.exam.Quiz;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz,Long>{

    public List<Quiz> findByCategory(Category cat);
    List<Quiz> findByFaculty(Faculty faculty);



}