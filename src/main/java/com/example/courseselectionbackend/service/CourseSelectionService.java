package com.example.courseselectionbackend.service;

import com.example.courseselectionbackend.model.CourseSelection;
import com.example.courseselectionbackend.repository.CourseSelectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CourseSelectionService {

	private final CourseSelectionRepository courseSelectionRepository;

	public CourseSelectionService(CourseSelectionRepository courseSelectionRepository) {
		this.courseSelectionRepository = courseSelectionRepository;
	}

	@Transactional
	public List<CourseSelection> getAll() {
		return courseSelectionRepository.findAll();
	}

	@Transactional
	public void add(CourseSelection a) {
		courseSelectionRepository.save(a);
	}
}
