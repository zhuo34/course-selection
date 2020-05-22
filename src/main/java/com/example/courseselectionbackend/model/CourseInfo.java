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
	@Column(name = "course_id")
	private String courseId;

	@Column(name = "course_name")
	private String courseName;

	@OneToMany(mappedBy = "courseInfo")
	private Set<CourseClass> courseClasses;
}
