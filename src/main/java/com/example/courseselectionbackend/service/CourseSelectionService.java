package com.example.courseselectionbackend.service;

import com.example.courseselectionbackend.model.Program;
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

	public List<Map<String, Object>> getClassesOfCourse(String courseId, String stuId) {
		return queryManager.findClassInfoByCourseId(courseId, stuId);
	}

	public List<Map<String, Object>> getSelectedCourses(String stuId) {
		return queryManager.findSelectedClassInfoByStuId(stuId);
	}

	public List<Map<String, Object>> searchCourses(String stuId, String courseId, String courseName, String tName, String cTime) {
		return queryManager.findAllCoursesByConditions(stuId, courseId, courseName, tName, cTime);
	}

	public List<Map<String, Object>> getPrograms(String stuId) {
		return queryManager.findAllProgramsOfStudent(stuId);
	}

	@Transactional
	public void saveProgram(Program.Request request) {
		queryManager.insertPrograms(request.getStuId(), request.getInsert());
		queryManager.deletePrograms(request.getStuId(), request.getDelete());
	}

	@Transactional
	public void submitProgram(String stuId) {
		queryManager.submitProgram(stuId);
	}
}
