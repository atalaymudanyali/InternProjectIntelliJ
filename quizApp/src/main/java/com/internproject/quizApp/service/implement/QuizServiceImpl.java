package com.internproject.quizApp.service.implement;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.internproject.quizApp.model.User;
import com.internproject.quizApp.model.exam.Faculty;
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
    @Autowired
    private com.internproject.quizApp.repo.userRepository userRepository;

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
    @Override
    public void assignQuizToFaculty(Long quizId, Faculty faculty) {
        // Fetch the quiz
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new RuntimeException("Quiz not found"));

        // Fetch all students in the given faculty
        List<User> students = userRepository.findByFaculty(faculty);

        // Assign the quiz to each student
        for (User student : students) {
            student.getQuizzes().add(quiz);
            userRepository.save(student);
        }
    }
    @Override
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    @Override
    public List<Quiz> getQuizzesByFaculty(Faculty faculty) {
        return quizRepository.findByFaculty(faculty);
    }
}
