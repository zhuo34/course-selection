package com.example.courseselectionbackend.querydsl;

import com.alibaba.fastjson.JSON;
import com.example.courseselectionbackend.model.*;
import com.example.courseselectionbackend.repository.CourseSelectionRepository;
import com.example.courseselectionbackend.repository.ProgramRepository;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class QueryDslManager {

	@Autowired
	private CourseSelectionRepository courseSelectionRepository;

	@Autowired
	private ProgramRepository programRepository;

	public void saveCourseSelection(CourseSelection courseSelection) {
		courseSelectionRepository.save(courseSelection);
	}

	public void saveCourseSelection(List<CourseSelection> courseSelection) {
		courseSelectionRepository.saveAll(courseSelection);
	}

	public void saveProgram(Program program) {
		programRepository.save(program);
	}

	public void saveProgram(List<Program> program) {
		programRepository.saveAll(program);
	}

	@PersistenceContext
	private EntityManager entityManager;
	private JPAQueryFactory queryFactory = null;

	public JPAQueryFactory qf() {
		if (queryFactory == null) {
			queryFactory = new JPAQueryFactory(entityManager);
		}
		return queryFactory;
	}
	private QStudent student = QStudent.student;
	private QTeacher teacher = QTeacher.teacher;
	private QProgram program = QProgram.program;
	private QCourseInfo courseInfo = QCourseInfo.courseInfo;
	private QCourseClass courseClass = QCourseClass.courseClass;
	private QCourseSelection courseSelection = QCourseSelection.courseSelection;

	public List<Map<String, Object>> findSelectedCourseInfoByStuId(String stuId) {
		List<String> names = new ArrayList<String>(){{
			add("isOn"); add("cid"); add("tname"); add("time"); add("place");
		}};
		List<Tuple> tuples = qf().select(courseSelection.isOn, courseClass.courseId, teacher.teaName,
					courseClass.time, courseClass.place)
				.from(courseSelection, student, courseClass, teacher)
				.where(student.stuId.eq(stuId), student.stuId.eq(courseSelection.id.stuId),
						courseSelection.id.classId.eq(courseClass.classId), courseClass.teaId.eq(teacher.teaId))
				.fetch();
		return tuplesToMapList(tuples, names);
	}

	private List<Map<String, Object>> tuplesToMapList(List<Tuple> tuples, List<String> names) {
		return tuples.stream().map(x -> tupleToMap(x, names)).collect(Collectors.toList());
	}

	private Map<String, Object> tupleToMap(Tuple tuple, List<String> names) {
		return IntStream.range(0, tuple.size()).boxed().collect(Collectors.toMap(
				names::get,
				i -> {
					Object obj = tuple.get(i, Object.class);
					if (obj == null) {
						return ""; // TODO: Exception here?
					}
					return obj;
				}));
	}
}
