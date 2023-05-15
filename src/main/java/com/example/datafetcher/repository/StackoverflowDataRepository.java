package com.example.datafetcher.repository;

import com.example.datafetcher.model.Owner;
import com.example.datafetcher.model.StackoverflowData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StackoverflowDataRepository extends JpaRepository<StackoverflowData, Long> {
}



