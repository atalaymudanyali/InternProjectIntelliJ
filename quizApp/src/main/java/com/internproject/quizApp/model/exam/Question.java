package com.internproject.quizApp.model.exam;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Question implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long questionId;

    private double questionWeight;

    @Enumerated(EnumType.STRING)
    private Faculty faculty;

    private String content;

    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String option5;

    private String answer;

    @ManyToOne(fetch = FetchType.EAGER)
    private Quiz quiz;

    public Question(long questionId, double questionWeight, Faculty faculty, String content, String option1, String option2, String option3, String option4, String option5, String answer, Quiz quiz) {
        this.questionId = questionId;
        this.questionWeight = questionWeight;
        this.faculty = faculty;
        this.content = content;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.option5 = option5;
        this.answer = answer;
        this.quiz = quiz;
    }
}
