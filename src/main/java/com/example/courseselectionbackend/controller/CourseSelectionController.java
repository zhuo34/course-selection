package com.example.courseselectionbackend.controller;

import com.example.courseselectionbackend.model.Student;
import com.example.courseselectionbackend.service.CourseSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CourseSelectionController {

	@Autowired
	CourseSelectionService courseSelectionService;

	@CrossOrigin
	@PostMapping("/get-classes")
	public List<Map<String, Object>> getClasses(@RequestBody Map<String, Object> params) {
		String courseId = params.get("courseId").toString();
		String stuId = params.get("stuId").toString();
		return courseSelectionService.getClasses(courseId, stuId);
	}

	@CrossOrigin
	@GetMapping("/get-courses")
	public List<Map<String, Object>> getCourses(@RequestParam String stuId) {
		return courseSelectionService.getCourses(stuId);
	}

	@CrossOrigin
	@GetMapping("/search-courses")
	public List<Map<String, Object>> searchCourses(@RequestParam String stuId, @RequestParam String courseId, @RequestParam String courseName, @RequestParam String tName, @RequestParam String cTime) {
		return courseSelectionService.searchCourses(stuId, courseId, courseName, tName, cTime);
	}
}
