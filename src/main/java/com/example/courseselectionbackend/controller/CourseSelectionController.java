package com.example.courseselectionbackend.controller;

import com.example.courseselectionbackend.model.Program;
import com.example.courseselectionbackend.service.CourseSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CourseSelectionController {

	@Autowired
	CourseSelectionService courseSelectionService;

	@CrossOrigin
	@PostMapping("/get-classes")
	public List<Map<String, Object>> getClassesOfCourse(@RequestBody Map<String, Object> params) {
		String courseId = params.get("courseId").toString();
		String stuId = params.get("stuId").toString();
		return courseSelectionService.getClassesOfCourse(courseId, stuId);
	}

	@CrossOrigin
	@GetMapping("/get-courses")
	public List<Map<String, Object>> getSelectedCourses(@RequestParam String stuId) {
		return courseSelectionService.getSelectedCourses(stuId);
	}

	@CrossOrigin
	@GetMapping("/search-courses")
	public List<Map<String, Object>> searchCourses(@RequestParam(defaultValue = "") String stuId, @RequestParam(defaultValue = "") String courseId, @RequestParam(defaultValue = "") String courseName, @RequestParam(defaultValue = "") String tName, @RequestParam(defaultValue = "") String cTime) {
		return courseSelectionService.searchCourses(stuId, courseId, courseName, tName, cTime);
	}

	@CrossOrigin
	@GetMapping("/get-program")
	public List<Map<String, Object>> searchCourses(@RequestParam String stuId) {
		System.out.println(stuId);
		return courseSelectionService.getPrograms(stuId);
	}

	@CrossOrigin
	@PostMapping("/save-program")
	public void saveProgram(@RequestBody Program.Request request) {
		courseSelectionService.saveProgram(request);
	}

	@CrossOrigin
	@PostMapping("/submit-program")
	public void saveProgram(@RequestBody String stuId) {
		courseSelectionService.submitProgram(stuId);
	}

}
