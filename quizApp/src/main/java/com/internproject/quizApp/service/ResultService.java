package com.internproject.quizApp.service;

import java.util.List;

import com.internproject.quizApp.model.User;
import com.internproject.quizApp.model.exam.Quiz;
import com.internproject.quizApp.model.exam.Result;

public interface ResultService {

    public Result addResult(Result result);
    public List<Result> getAllResult();
    public List<Result> getResultOfQuiz(Quiz quiz);
    public List<Result> getResultOfUser(User user);
    public List<Result> getResultOfUserAndQuiz(Quiz quiz,User user);

}
