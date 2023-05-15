package com.example.datafetcher.repository;

import com.example.datafetcher.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

interface OwnerRepository extends JpaRepository<Owner, Long> {
}