package com.internproject.quizApp.controller;

import com.internproject.quizApp.model.exam.Question;
import com.internproject.quizApp.model.exam.Quiz;
import com.internproject.quizApp.service.QuestionService;
import com.internproject.quizApp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionRestController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private QuizService quizService;

    // Create a new question
    @PostMapping("/quiz/{quizId}")
    public ResponseEntity<Question> addQuestion(@PathVariable Long quizId, @RequestBody Question question) {
        Quiz quiz = quizService.getQuiz(quizId);
        question.setQuiz(quiz);
        Question createdQuestion = questionService.addQuestion(question);
        return ResponseEntity.ok(createdQuestion);
    }

    // Get all questions for a quiz
    @GetMapping("/quiz/{quizId}")
    public ResponseEntity<List<Question>> getQuestionsByQuiz(@PathVariable Long quizId) {
        List<Question> questions = questionService.getQuestionsByQuiz(quizId);
        return ResponseEntity.ok(questions);
    }

    // Get a specific question by ID
    @GetMapping("/{questionId}")
    public ResponseEntity<Question> getQuestionById(@PathVariable Long questionId) {
        Question question = questionService.getQuestion(questionId);
        return ResponseEntity.ok(question);
    }

    // Update a question
    @PutMapping("/{questionId}")
    public ResponseEntity<Question> updateQuestion(@PathVariable Long questionId, @RequestBody Question questionDetails) {
        Question existingQuestion = questionService.getQuestion(questionId);
        existingQuestion.setContent(questionDetails.getContent());
        existingQuestion.setAnswer(questionDetails.getAnswer());
        // Set other fields as necessary
        Question updatedQuestion = questionService.updateQuestion(existingQuestion);
        return ResponseEntity.ok(updatedQuestion);
    }

    // Delete a question
    @DeleteMapping("/{questionId}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.noContent().build();
    }
}
