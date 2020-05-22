package com.example.courseselectionbackend.model;

import com.example.courseselectionbackend.model.primarykey.CourseSelectionPK;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "course_selection")
public class CourseSelection {

	@EmbeddedId
	private CourseSelectionPK id;

	@ManyToOne
	@MapsId("stu_id")
	@JoinColumn(name = "stu_id")
	private Student student;

	@ManyToOne
	@MapsId("class_id")
	@JoinColumn(name = "class_id")
	private CourseClass courseClass;

	@Column(name = "is_on")
	private boolean isOn;
}
