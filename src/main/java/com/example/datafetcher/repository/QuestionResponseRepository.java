package com.example.datafetcher.repository;

import com.example.datafetcher.model.QuestionResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionResponseRepository extends JpaRepository<QuestionResponse, Long> {
}
