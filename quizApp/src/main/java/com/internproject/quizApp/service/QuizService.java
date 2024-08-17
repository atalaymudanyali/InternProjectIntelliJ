package com.internproject.quizApp.service;

import java.util.List;
import java.util.Set;

import com.internproject.quizApp.model.exam.Category;
import com.internproject.quizApp.model.exam.Quiz;


public interface QuizService {
    public Quiz addQuiz(Quiz quiz);
    public Quiz updateQuiz(Quiz quiz);
    public Set<Quiz> getQuizzes();
    public Quiz getQuiz(Long quizId);
    public void deleteQuiz(Long quizId);
    public List<Quiz> getQuizzesOfCategory(Category cat);

}
