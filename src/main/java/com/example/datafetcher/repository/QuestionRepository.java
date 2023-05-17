package com.example.datafetcher.repository;

import com.example.datafetcher.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
