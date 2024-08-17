package com.internproject.quizApp.model;


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

public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private  long userRoleId;


    @ManyToOne(fetch = FetchType.LAZY) // OR EAGER ????
    private  User user;

    @ManyToOne
    private  Role role;



}