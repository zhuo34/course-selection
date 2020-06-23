package com.example.courseselectionbackend.service;

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

	public List<Map<String, Object>> getClasses(String courseId, String stuId) {
		return queryManager.findClassInfoByCourseId(courseId, stuId);
	}

	public List<Map<String, Object>> getCourses(String stuId) {
		return queryManager.findSelectedClassInfoByStuId(stuId);
	}

	public List<Map<String, Object>> searchCourses(String stuId, String courseId, String courseName, String tName, String cTime) {
		return queryManager.findAllCoursesByConditions(stuId, courseId, courseName, tName, cTime);
	}
}
