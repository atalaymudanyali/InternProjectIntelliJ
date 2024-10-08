
package com.internproject.quizApp.model;

import com.internproject.quizApp.model.exam.Faculty;
import com.internproject.quizApp.model.exam.Quiz;
import com.internproject.quizApp.model.exam.Result;

import jakarta.persistence.*;
import lombok.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String userName;
    private String password;
    private String fName;
    private String lname;
    private String email;
    private String phone;
    private boolean enabled = true;
    private String profile;

    @Enumerated(EnumType.STRING)
    private Faculty faculty;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnore
    private List<Result> results = new ArrayList<>();

    @Getter
    @ManyToMany
    @JoinTable(
            name = "user_quiz",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "quiz_id")
    )
    private Set<Quiz> quizzes = new HashSet<>();



    public User(Long id, String userName, String password, String fName, String lname, String email, String phone,
                boolean enabled, Faculty faculty) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.fName = fName;
        this.lname = lname;
        this.email = email;
        this.phone = phone;
        this.enabled = enabled;
        this.faculty = faculty;
    }
}
