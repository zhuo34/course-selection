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
	@Column(name = "ccno", precision = 11)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer classId;

	@Column(name = "cid", length = 10)
	private String courseId;

	@Column(name = "ctid", length = 10)
	private String teaId;

	@Column(name = "ctime", nullable = false)
	private String time;

	@Column(name = "cplace", nullable = false)
	private String place;

	@Column(name = "capacity", nullable = false)
	private int capacity;

	@Column(name = "examtime", length = 255)
	private String examTime;
}
