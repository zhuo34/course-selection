package com.example.courseselectionbackend.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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

	@Column(name = "semail", length = 40)
	private String stuEmail;

	@Column(name = "sprogram")
	private Boolean stuProgramConfirmed;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "sphoto", columnDefinition="blob")
	private Byte[] stuPhoto;

//	@Fetch(FetchMode.JOIN)
//	@OneToMany(mappedBy = "student")
//	private Set<CourseSelection> selectedCourses;
//
//	@Fetch(FetchMode.JOIN)
//	@OneToMany(mappedBy = "student")
//	private Set<Program> programs;
}
