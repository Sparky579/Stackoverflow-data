package com.example.datafetcher.repository;

import com.example.datafetcher.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    boolean existsByUserId(int userId);

    List<Owner> findByUserId(int userId);
}