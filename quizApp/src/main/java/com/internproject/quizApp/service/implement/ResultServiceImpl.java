package com.internproject.quizApp.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.internproject.quizApp.model.User;
import com.internproject.quizApp.model.exam.Quiz;
import com.internproject.quizApp.model.exam.Result;
import com.internproject.quizApp.repo.ResultRepository;
import com.internproject.quizApp.service.ResultService;

@Service
public class ResultServiceImpl implements ResultService {

    @Autowired
    private ResultRepository resultrepository;

    @Override
    public Result addResult(Result result) {
        return this.resultrepository.save(result);
    }

    @Override
    public List<Result> getAllResult() {
        return this.resultrepository.findAll();
    }

    @Override
    public List<Result> getResultOfQuiz(Quiz quiz) {
        return this.resultrepository.findByQuiz(quiz);
    }

    @Override
    public List<Result> getResultOfUser(User user) {
        return this.resultrepository.findByUser(user);
    }

    @Override
    public List<Result> getResultOfUserAndQuiz(Quiz quiz, User user) {
        return this.resultrepository.findByQuizAndUser(quiz,user);
    }

}
