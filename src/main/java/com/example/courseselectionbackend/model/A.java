package com.example.courseselectionbackend.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "a")
public class A {

	@Id
	private Long id;

	private String name;

	public A() {}

	public A(Long id, String name) {
		setId(id);
		setName(name);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "{ id: " + String.valueOf(id) + ", name: " + name + " }";
	}
}
