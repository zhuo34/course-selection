package com.example.courseselectionbackend.controller;

import com.example.courseselectionbackend.model.CourseSelection;
import com.example.courseselectionbackend.service.CourseSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/a")
public class CourseSelectionController {

	@Autowired
	CourseSelectionService courseSelectionService;

	@GetMapping("/")
	public List<CourseSelection> getAllA() {
		List<CourseSelection> l = courseSelectionService.getAll();
		for (CourseSelection a: l) {
			System.out.println(a);
		}
		return l;
	}

	@GetMapping("/add")
	public void addA() {
		Long id = (long)(Math.random() * 1000);
//		courseSelectionService.add(new CourseSelection(id, "a-" + String.valueOf(id)));
	}
}
