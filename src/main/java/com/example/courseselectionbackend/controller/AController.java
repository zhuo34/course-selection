package com.example.courseselectionbackend.controller;

import com.example.courseselectionbackend.model.A;
import com.example.courseselectionbackend.service.AService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/a")
public class AController {

	@Autowired
	AService aService;

	@GetMapping("/")
	public List<A> getAllA() {
		List<A> l = aService.getAll();
		for (A a: l) {
			System.out.println(a);
		}
		return l;
	}

	@GetMapping("/add")
	public void addA() {
		Long id = (long)(Math.random() * 1000);
		aService.add(new A(id, "a-" + String.valueOf(id)));
	}
}
