package com.internproject.quizApp.model.exam;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long quizId;

    private String title;
    private String description;
    private boolean active = false;
    private String maxMarks;
    private String noOfQuestions;


    @Column(nullable = false)
    private int numQuestions;

    @Column(nullable = false)
    private int durationMinutes;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private Faculty faculty;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Result> results = new ArrayList<>();

    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Question> questions = new HashSet<>();



    public void setNumQuestions(int numQuestions) {
        if (numQuestions < 5 || numQuestions > 20) {
            throw new IllegalArgumentException("Number of questions must be between 5 and 20.");
        }
        this.numQuestions = numQuestions;
    }

    public void setDurationMinutes(int durationMinutes) {
        if (durationMinutes < 5 || durationMinutes > 180) {
            throw new IllegalArgumentException("Duration must be between 5 minutes and 3 hours.");
        }
        this.durationMinutes = durationMinutes;
    }


    public Quiz(String title, String description, boolean active, String maxMarks, String noOfQuestions, Category category) {
    this.title = title;
    this.description = description;
    this.active = active;
    this.maxMarks = maxMarks;
    this.noOfQuestions = noOfQuestions;
    this.category = category;
}

public Quiz(long quizId, String title, String description, boolean active, String maxMarks, String noOfQuestions, Category category) {
    this.quizId = quizId;
    this.title = title;
    this.description = description;
    this.active = active;
    this.maxMarks = maxMarks;
    this.noOfQuestions = noOfQuestions;
    this.category = category;
}
}
