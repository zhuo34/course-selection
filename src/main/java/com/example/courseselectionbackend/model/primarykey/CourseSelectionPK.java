package com.example.courseselectionbackend.model.primarykey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CourseSelectionPK implements PrimaryKey {
	@Column(name = "stu_id", length = 10)
	private String stuId;

	@Column(name = "class_id")
	private Long classId;
}
