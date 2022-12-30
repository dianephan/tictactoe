package com.example.tictactoe.repository;
import com.example.tictactoe.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

}