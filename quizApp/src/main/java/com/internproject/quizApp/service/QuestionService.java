package com.internproject.quizApp.service;

import java.util.List;
import java.util.Set;

import com.internproject.quizApp.model.exam.Question;
import com.internproject.quizApp.model.exam.Quiz;

public interface QuestionService {
    public Question addQuestion(Question question);
    public Question updateQuestion(Question question);
    public Set<Question> getQuestions();
    public Question getQuestion(Long questionId);
    public  Set<Question> getQuestionOfQuiz(Quiz quiz);
    public void deleteQuestion(Long qid);
    double calculateTotalWeight(Long quizId);
    List<Question> getQuestionsByQuiz(Long quizId);

}
