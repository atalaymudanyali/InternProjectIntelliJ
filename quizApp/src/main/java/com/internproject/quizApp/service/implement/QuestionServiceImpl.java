package com.internproject.quizApp.service.implement;

import com.internproject.quizApp.model.exam.Question;
import com.internproject.quizApp.model.exam.Quiz;
import com.internproject.quizApp.repo.QuestionRepository;
import com.internproject.quizApp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
@Service
public class QuestionServiceImpl implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public Question addQuestion(Question question) {
        return this.questionRepository.save(question);
    }

    @Override
    public Question updateQuestion(Question question) {
        return this.questionRepository.save(question);
    }

    @Override
    public Set<Question> getQuestions() {
        return new LinkedHashSet<>(this.questionRepository.findAll());
    }

    @Override
    public Question getQuestion(Long questionId) {
        return this.questionRepository.findById(questionId).get();
    }

    @Override
    public Set<Question> getQuestionOfQuiz(Quiz quiz) {
        return this.questionRepository.findByQuiz(quiz);
    }

    @Override
    public void deleteQuestion(Long qid) {
        this.questionRepository.deleteById(qid);
    }

    @Override
    public List<Question> getQuestionsByQuiz(Long quizId) {
        return questionRepository.findByQuizQuizId(quizId);
    }

    @Override
    public double calculateTotalWeight(Long quizId) {
        List<Question> questions = questionRepository.findByQuizQuizId(quizId);
        return questions.stream().mapToDouble(Question::getQuestionWeight).sum();
    }
}
