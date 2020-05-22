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
@Table(name = "course_class")
public class CourseClass {

	@Id
	@Column(name = "class_id")
	private Long classId;

	@ManyToOne
	@JoinColumn(name = "course_id")
	private CourseInfo courseInfo;

	@OneToMany(mappedBy = "courseClass")
	private Set<CourseSelection> selectedStudents;
}
