package com.example.datafetcher.repository;

import com.example.datafetcher.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query("SELECT MAX(e.answer_count) FROM Question e")
    Integer findMaxAnswerCount();

    @Query("SELECT Count(e.answer_count) FROM Question e where e.answer_count=0")
    Integer findZeroCount();
}