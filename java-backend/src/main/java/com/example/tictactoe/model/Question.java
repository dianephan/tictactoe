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

    public String getAns_one() {
        return ans_one;
    }

    public String getAns_two() {
        return ans_two;
    }

    public String getAns_three() {
        return ans_three;
    }

    public String getAns_four() {
        return ans_four;
    }

    public int getCorrect_ans() {
        return correct_ans;
    }

    @Column(name = "question_string")
    private String question;

    @Column(name = "ans_one")
    private String ans_one;

    @Column(name = "ans_two")
    private String ans_two;

    @Column(name = "ans_three")
    private String ans_three;

    @Column(name = "ans_four")
    private String ans_four;

    @Column(name = "correct_ans")
    private int correct_ans;
}