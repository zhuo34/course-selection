package com.example.courseselectionbackend.model;

import com.example.courseselectionbackend.model.primarykey.ProgramPK;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "program")
public class Program {

	@EmbeddedId
	private ProgramPK id;

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	static public class Request {
		private String stuId;
		private List<String> insert;
		private List<String> delete;
	}
}
