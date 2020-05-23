package com.example.courseselectionbackend.repository;

import com.example.courseselectionbackend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
}
