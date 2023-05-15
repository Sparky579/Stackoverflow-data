package com.example.datafetcher.repository;

import com.example.datafetcher.model.StackoverflowResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StackoverflowResponseRepository extends JpaRepository<StackoverflowResponse, Long> {
}
