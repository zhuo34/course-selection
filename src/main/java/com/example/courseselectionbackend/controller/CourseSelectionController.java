package com.example.courseselectionbackend.controller;

import com.example.courseselectionbackend.model.CourseSelection;
import com.example.courseselectionbackend.model.Program;
import com.example.courseselectionbackend.model.Student;
import com.example.courseselectionbackend.model.primarykey.ProgramPK;
import com.example.courseselectionbackend.service.CourseSelectionService;
import com.querydsl.core.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/a")
public class CourseSelectionController {

	@Autowired
	CourseSelectionService courseSelectionService;

	@GetMapping("/")
	public List<Student> getAllA() {
		List<Student> l = courseSelectionService.getAll();
		for (Student a: l) {
			System.out.println(a);
		}
		return l;
	}

	@GetMapping("/get")
	public List<Map<String, Object>> get() {
		return courseSelectionService.get();
	}
}
