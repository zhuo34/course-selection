package com.example.courseselectionbackend.controller;

import com.example.courseselectionbackend.model.Student;
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
	public List<Map<String, Object>> getClasses(@RequestBody Map<String, Object> params) {
		String courseId = params.get("courseId").toString();
		String stuId = params.get("stuId").toString();
		System.out.println(courseId);
		System.out.println(stuId);
		return courseSelectionService.getClasses(courseId, stuId);
	}

}
