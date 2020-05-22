package com.example.courseselectionbackend.service;

import com.example.courseselectionbackend.model.A;
import com.example.courseselectionbackend.repository.ARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AService {

	@Autowired
	private ARepository aRepository;

	public List<A> getAll() {
		return aRepository.findAll();
	}

	public void add(A a) {
		aRepository.save(a);
	}
}
