package com.example.datafetcher.repository;

import com.example.datafetcher.model.APIs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface APIsRepository extends JpaRepository<APIs, Long> {
    APIs findByName(String name);
}
