package com.example.tictactoe.model;

import javax.persistence.*;

@Entity
@Table (name = "questions")
public class Question {
    public Long getId() {
        return id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "questionID")
        private Long id;

    public String getQuestion() {
        return question;
    }

    @Column(name = "question_string")
    private String question;
}
