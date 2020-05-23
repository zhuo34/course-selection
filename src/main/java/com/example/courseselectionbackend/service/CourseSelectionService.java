package com.example.courseselectionbackend.service;

import com.example.courseselectionbackend.model.CourseSelection;
import com.example.courseselectionbackend.model.QCourseSelection;
import com.example.courseselectionbackend.repository.CourseSelectionRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class CourseSelectionService {

	@Autowired
	private CourseSelectionRepository courseSelectionRepository;

	@Transactional
	public List<CourseSelection> getAll() {
		return courseSelectionRepository.findAll();
	}

	@Transactional
	public void add(CourseSelection a) {
		courseSelectionRepository.save(a);
	}
}
