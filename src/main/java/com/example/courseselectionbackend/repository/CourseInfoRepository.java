package com.example.courseselectionbackend.repository;

import com.example.courseselectionbackend.model.CourseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseInfoRepository extends JpaRepository<CourseInfo, String> {
}
