package com.example.courseselectionbackend.model;


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
@Table(name = "major")
public class Major {

	@Id
	@Column(name = "smajor", length = 40)
	private String stuMajor;

	@Column(name = "minTotalCredit", nullable = false)
	private double minTotalCredit;

	@Column(name = "minOptionalCredit", nullable = false)
	private double minOptionalCredit;

	@Column(name = "minPublicCredit", nullable = false)
	private double minPublicCredit;
}
