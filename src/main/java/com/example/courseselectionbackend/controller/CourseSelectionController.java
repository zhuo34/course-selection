package com.example.courseselectionbackend.controller;

import com.example.courseselectionbackend.model.CourseSelection;
import com.example.courseselectionbackend.model.Program;
import com.example.courseselectionbackend.model.primarykey.CourseSelectionPK;
import com.example.courseselectionbackend.service.CourseSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class CourseSelectionController {

	@Autowired
	CourseSelectionService courseSelectionService;

	@CrossOrigin
	@GetMapping("/get-classes")
	public List<Map<String, Object>> getClassesOfCourse(@RequestParam String courseId, @RequestParam String stuId) {
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
	public Map<String, Object> searchCourses(@RequestParam String stuId) {
		return courseSelectionService.getPrograms(stuId);
	}

	@CrossOrigin
	@PostMapping("/submit-program")
	public void submitProgram(@RequestBody Program.Request request) {
		courseSelectionService.submitProgram(request);
	}

	@CrossOrigin
	@GetMapping("/is-program-finished")
	public Map<String, Boolean> getProgramState(@RequestParam String stuId) {
		System.out.println(stuId);
		Map<String, Boolean> ret = new HashMap<>();
		ret.put("isFinished", courseSelectionService.isProgramFinished(stuId));
		System.out.println(ret.get("isFinished"));
		return ret;
	}

	@CrossOrigin
	@PostMapping("/select-class")
	public void selectClass(@RequestBody CourseSelectionPK id) {
		courseSelectionService.selectClass(id);
	}

	@CrossOrigin
	@PostMapping("/delete-class")
	public void deleteClass(@RequestBody CourseSelectionPK id) {
		courseSelectionService.deleteClass(id);
	}
}
