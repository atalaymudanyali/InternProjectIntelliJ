package com.internproject.quizApp.repo;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.internproject.quizApp.model.User;
import com.internproject.quizApp.model.exam.Quiz;
import com.internproject.quizApp.model.exam.Result;

public interface ResultRepository extends JpaRepository<Result, Integer> {

    List<Result> findByQuiz(Quiz quiz);

    List<Result> findByUser(User user);

    List<Result> findByQuizAndUser(Quiz quiz, User user);

}