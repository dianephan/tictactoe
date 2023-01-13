package com.example.tictactoe.repository;
import com.example.tictactoe.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Random;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Random r = new Random();
    default Optional<Question> getRandomQuestion(){
        long totalNumberOfQuestions = this.count();
        long thisQuestionIndex = r.nextLong(1, totalNumberOfQuestions+1);
        System.out.println("[DATA] thisquestion = " + thisQuestionIndex);
        return this.findById(thisQuestionIndex);
    }
}