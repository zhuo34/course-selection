package com.example.courseselectionbackend.repository;

import com.example.courseselectionbackend.model.A;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ARepository extends JpaRepository<A, Long> {
}
