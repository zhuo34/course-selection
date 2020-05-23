package com.example.courseselectionbackend.model.primarykey;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ProgramPK implements PrimaryKey {
	@Column(name = "sid", length = 10)
	private String stuId;

	@Column(name = "cid", length = 10)
	private String courseId;
}
