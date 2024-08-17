package com.internproject.quizApp.service.implement;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.internproject.quizApp.model.exam.Category;
import com.internproject.quizApp.model.exam.Quiz;
import com.internproject.quizApp.repo.QuizRepository;
import com.internproject.quizApp.service.QuizService;

@Service // BUNA BAK

public class QuizServiceImpl implements QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Override
    public Quiz addQuiz(Quiz quiz) {
        return this.quizRepository.save(quiz);
    }

    @Override
    public Quiz updateQuiz(Quiz quiz) {
        return this.quizRepository.save(quiz);
    }

    @Override
    public Set<Quiz> getQuizzes() {
        return new LinkedHashSet<>(this.quizRepository.findAll());
    }

    @Override
    public Quiz getQuiz(Long quizId) {
        return this.quizRepository.findById(quizId).get();
    }

    @Override
    public void deleteQuiz(Long quizId) {
        this.quizRepository.deleteById(quizId);
    }

    @Override
    public java.util.List<Quiz> getQuizzesOfCategory(Category cat) {
        return this.quizRepository.findByCategory(cat);
    }

}
