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
@Table(name = "student")
public class Student {

	@Id
	@Column(name = "sid", length = 10)
	private String stuId;

	@Column(name = "sname", length = 40)
	private String stuName;

	@Column(name = "smajor", length = 40)
	private String stuMajor;

	@Column(name = "sclass", length = 40)
	private String stuClass;

	@Column(name = "scollege", length = 40)
	private String stuCollege;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "sphoto", columnDefinition="blob")
	private byte[] stuPhoto;

	@OneToMany(mappedBy = "student")
	private Set<CourseSelection> selectedCourses;
}
