package com.example.datafetcher.repository;

import com.example.datafetcher.model.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    @Query(value = "SELECT * FROM answer WHERE question_id = :questionId", nativeQuery = true)
    List<Answer> findAnswersByQuestionId(int questionId);

}



