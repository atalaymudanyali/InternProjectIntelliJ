package com.internproject.quizApp.repo;


import com.internproject.quizApp.model.exam.Question;
import com.internproject.quizApp.model.exam.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Set<Question> findByQuiz(Quiz quiz);

    // Updated method to reflect the correct property name in Quiz
    List<Question> findByQuizQuizId(Long quizId);
}
