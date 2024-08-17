package com.internproject.quizApp.controller;

import com.internproject.quizApp.model.exam.Quiz;
import com.internproject.quizApp.service.QuizService;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.faces.view.ViewScoped;

@Component("quizController")
@ViewScoped
public class QuizController {

    @Autowired
    private QuizService quizService;

    @Getter @Setter
    private int numQuestions;

    @Getter @Setter
    private int examDuration;

    @Getter @Setter
    private String title;

    public void createQuiz() {
        try {
            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setNumQuestions(numQuestions);
            quiz.setDurationMinutes(examDuration);
            quizService.addQuiz(quiz);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Quiz Created", "Your quiz has been created successfully."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
        }
    }
}
