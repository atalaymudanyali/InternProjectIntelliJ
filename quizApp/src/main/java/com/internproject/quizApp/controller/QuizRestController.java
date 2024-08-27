package com.internproject.quizApp.controller;

import com.internproject.quizApp.model.exam.Quiz;
import com.internproject.quizApp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/quizzes")
public class QuizRestController {

    @Autowired
    private QuizService quizService;

    @PostMapping
    public ResponseEntity<Quiz> createQuiz(@RequestBody Quiz quiz) {
        Quiz createdQuiz = quizService.addQuiz(quiz);
        return ResponseEntity.ok(createdQuiz);
    }

    @GetMapping
    public ResponseEntity<Set<Quiz>> getAllQuizzes() {
        Set<Quiz> quizzes = quizService.getQuizzes();
        return ResponseEntity.ok(quizzes);
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<Quiz> getQuizById(@PathVariable Long quizId) {
        Quiz quiz = quizService.getQuiz(quizId);
        return ResponseEntity.ok(quiz);
    }


    @PutMapping("/{quizId}")
    public ResponseEntity<Quiz> updateQuiz(@PathVariable Long quizId, @RequestBody Quiz quizDetails) {
        Quiz existingQuiz = quizService.getQuiz(quizId);
        existingQuiz.setTitle(quizDetails.getTitle());
        existingQuiz.setNumQuestions(quizDetails.getNumQuestions());
        existingQuiz.setDurationMinutes(quizDetails.getDurationMinutes());
        Quiz updatedQuiz = quizService.updateQuiz(existingQuiz);
        return ResponseEntity.ok(updatedQuiz);
    }

        @DeleteMapping("/{quizId}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long quizId) {
        quizService.deleteQuiz(quizId);
        return ResponseEntity.noContent().build();
    }
}
