package com.example.courseselectionbackend.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "course_info")
public class CourseInfo {

	@Id
	@Column(name = "cid", length = 10)
	private String courseId;

	@Column(name = "cname", length = 40, nullable = false)
	private String courseName;

	@Column(name = "ccredits", nullable = false)
	private double courseCredits;

	@Column(name = "cexammode", length = 40, nullable = false)
	private String courseExamMode;

	@Column(name = "ctype", nullable = false)
	private boolean ctype;

	@OneToMany(mappedBy = "courseInfo")
	private Set<CourseClass> courseClasses;

	@OneToMany(mappedBy = "course")
	private Set<Program> programs;
}
