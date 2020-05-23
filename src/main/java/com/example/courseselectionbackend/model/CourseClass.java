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

	@ManyToOne
	@JoinColumn(name = "cid", foreignKey = @ForeignKey(name = "fk5"), nullable = false)
	private CourseInfo courseInfo;

	@ManyToOne
	@JoinColumn(name = "tid", foreignKey = @ForeignKey(name = "fk6"), nullable = false)
	private Teacher teacher;

	@Column(name = "ctime", nullable = false)
	private String time;

	@Column(name = "cplace", nullable = false)
	private String place;

	@Column(name = "capacity", nullable = false)
	private int capacity;

	@OneToMany(mappedBy = "courseClass")
	private Set<CourseSelection> selectedStudents;
}
