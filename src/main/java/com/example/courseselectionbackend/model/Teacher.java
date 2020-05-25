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
@Table(name = "teacher")
public class Teacher {

	@Id
	@Column(name = "tid", length = 10)
	private String teaId;

	@Column(name = "tname", length = 40)
	private String teaName;

	@Column(name = "tcollege", length = 40)
	private String teaCollege;

	@Column(name = "temail", length = 40)
	private String teaEmail;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "tphoto", columnDefinition="blob")
	private byte[] teaPhoto;

//	@OneToMany(mappedBy = "teacher")
//	private Set<CourseClass> teaClasses;
}
