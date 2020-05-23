package com.example.courseselectionbackend.repository;

import com.example.courseselectionbackend.model.Program;
import com.example.courseselectionbackend.model.primarykey.ProgramPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramRepository extends JpaRepository<Program, ProgramPK> {
}
