package com.example.courseselectionbackend.service;

import com.example.courseselectionbackend.model.QStudent;
import com.example.courseselectionbackend.model.Student;
import com.example.courseselectionbackend.querydsl.QueryDslManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
public class CourseSelectionService {

	@Autowired
	private QueryDslManager queryManager;

	@Transactional
	public List<Student> getAll() {
		return queryManager.qf().selectFrom(QStudent.student).fetch();
	}

	@Transactional
	public List<Map<String, Object>> get() {
		return queryManager.findSelectedCourseInfoByStuId("3170756898");
	}
}
