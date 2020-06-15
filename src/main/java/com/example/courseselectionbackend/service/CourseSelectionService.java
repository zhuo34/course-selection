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
	public List<Map<String, Object>> getClasses(String courseId, String stuId) {
		return queryManager.findAllCourseClassInfoByCourseId(courseId, stuId);
	}
}
