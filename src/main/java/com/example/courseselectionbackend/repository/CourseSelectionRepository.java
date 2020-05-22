package com.example.courseselectionbackend.repository;

import com.example.courseselectionbackend.model.CourseSelection;
import com.example.courseselectionbackend.model.primarykey.CourseSelectionPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseSelectionRepository extends JpaRepository<CourseSelection, CourseSelectionPK> {
}
