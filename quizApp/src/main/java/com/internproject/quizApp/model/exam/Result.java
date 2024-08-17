package com.internproject.quizApp.model.exam;


import com.internproject.quizApp.model.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int result_id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Quiz quiz;

    @ManyToOne(fetch = FetchType.LAZY) // OR EAGER ????
    private User user;

    private int qAttempted;

    private int correctAns;

    private double marksScored;

    private String submitDateTime;



    public Result(int result_id, Quiz quiz, User user, int qAttempted, int correctAns, double marksScored) {
        super();
        this.result_id = result_id;
        this.quiz = quiz;
        this.user = user;
        this.qAttempted = qAttempted;
        this.correctAns = correctAns;
        this.marksScored = marksScored;
    }
}