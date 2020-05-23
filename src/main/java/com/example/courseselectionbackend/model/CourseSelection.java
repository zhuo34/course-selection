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
	@MapsId("sid")
	@JoinColumn(name = "sid", foreignKey = @ForeignKey(name = "fk7"), nullable = false)
	private Student student;

	@ManyToOne
	@MapsId("ccno")
	@JoinColumn(name = "ccno", foreignKey = @ForeignKey(name = "fk8"), nullable = false)
	private CourseClass courseClass;

	@Column(name = "ison", nullable = false)
	private boolean isOn;
}
