package com.internproject.quizApp.controller;

import com.internproject.quizApp.model.exam.Question;
import com.internproject.quizApp.model.exam.Faculty;
import com.internproject.quizApp.model.exam.Quiz;
import com.internproject.quizApp.service.QuestionService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.annotation.SessionScope;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Controller
@SessionScope
@ViewScoped
public class QuestionController implements Serializable {

    @Autowired
    private QuestionService questionService;

    @Getter
    @Setter
    private Question question;

    @Getter
    @Setter
    private List<Faculty> faculties;

    @Getter
    @Setter
    private int numOptions = 3;

    private final int MIN_OPTIONS = 3;
    private final int MAX_OPTIONS = 5;

    @PostConstruct
    public void init() {
        this.question = new Question();
        this.faculties = Arrays.asList(Faculty.values());
    }

    public void addOption() {
        if (numOptions < MAX_OPTIONS) {
            numOptions++;
        } else {
            throw new IllegalArgumentException("Maximum number of options is " + MAX_OPTIONS);
        }
    }

    public void removeOption() {
        if (numOptions > MIN_OPTIONS) {
            numOptions--;
        } else {
            throw new IllegalArgumentException("Minimum number of options is " + MIN_OPTIONS);
        }
    }

    public void createQuestion() {
        validateQuestion();
        autoCalculateWeightIfNeeded();
        this.questionService.addQuestion(this.question);
        this.question = new Question();
        numOptions = MIN_OPTIONS;
    }

    private void validateQuestion() {
        if (question.getQuestionWeight() < 0 || question.getQuestionWeight() > 100) {
            throw new IllegalArgumentException("Weight must be between 0 and 100.");
        }

        if (question.getAnswer() == null || question.getAnswer().isEmpty()) {
            throw new IllegalArgumentException("A correct answer must be provided.");
        }

        double totalWeight = questionService.calculateTotalWeight(question.getQuiz().getQuizId());
        if (totalWeight + question.getQuestionWeight() > 100) {
            throw new IllegalArgumentException("Total weight exceeds 100.");
        }

        if (numOptions < MIN_OPTIONS || numOptions > MAX_OPTIONS) {
            throw new IllegalArgumentException("Number of options must be between " + MIN_OPTIONS + " and " + MAX_OPTIONS);
        }
    }

    private void autoCalculateWeightIfNeeded() {
        int totalQuestions = question.getQuiz().getNumQuestions();
        int currentQuestionCount = question.getQuiz().getQuestions().size();

        if (question.getQuestionWeight() == 0) {
            double baseWeight = 100.0 / totalQuestions;
            double roundedWeight = Math.ceil(baseWeight * 10) / 10.0;
            question.setQuestionWeight(roundedWeight);
        }

        if (currentQuestionCount == totalQuestions) {
            double totalAssignedWeight = question.getQuiz().getQuestions().stream()
                    .mapToDouble(Question::getQuestionWeight)
                    .sum();

            double remainingWeight = 100.0 - totalAssignedWeight;

            if (remainingWeight > 0) {
                question.setQuestionWeight(remainingWeight);
            }
        }
    }



}
