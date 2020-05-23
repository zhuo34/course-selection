package com.example.courseselectionbackend.model;

import com.example.courseselectionbackend.model.primarykey.ProgramPK;
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
@Table(name = "program")
public class Program {

	@EmbeddedId
	private ProgramPK id;

	@ManyToOne
	@MapsId("sid")
	@JoinColumn(name = "sid", foreignKey = @ForeignKey(name = "fk10"), nullable = false)
	private Student student;

	@ManyToOne
	@MapsId("cid")
	@JoinColumn(name = "cid", foreignKey = @ForeignKey(name = "fk11"), nullable = false)
	private CourseInfo course;
}
