package com.example.courseselectionbackend.model;

import com.example.courseselectionbackend.model.primarykey.CourseSelectionPK;
import com.querydsl.core.annotations.QueryInit;
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

	@Column(name = "ison", nullable = false)
	private boolean isOn;
}
