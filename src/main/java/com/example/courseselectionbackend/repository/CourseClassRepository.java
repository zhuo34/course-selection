package com.example.courseselectionbackend.repository;

import com.example.courseselectionbackend.model.CourseClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseClassRepository extends JpaRepository<CourseClass, Long> {
}
