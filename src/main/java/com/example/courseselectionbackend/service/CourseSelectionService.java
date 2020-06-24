package com.example.courseselectionbackend.service;

import com.example.courseselectionbackend.model.Program;
import com.example.courseselectionbackend.model.Student;
import com.example.courseselectionbackend.querydsl.QueryDslManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
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

	@Transactional
	public Map<String, Object> getPrograms(String stuId) {
		int programState = queryManager.findProgramState(stuId);
		if (programState == 0) {
			queryManager.insertCompulsoryCourses(stuId);
			queryManager.startProgram(stuId);
		}
		HashMap<String, Object> ret = new HashMap<>();
		ret.put("courses", queryManager.findAllProgramsOfStudent(stuId));
		ret.put("isFinished", programState == 2);
		return ret;
	}

	@Transactional
	public void submitProgram(Program.Request request) {
		queryManager.insertPrograms(request.getStuId(), request.getInsert());
		queryManager.deletePrograms(request.getStuId(), request.getDelete());
		if (request.isSubmit())
			queryManager.submitProgram(request.getStuId());
	}
}
