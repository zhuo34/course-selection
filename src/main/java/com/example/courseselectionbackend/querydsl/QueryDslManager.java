package com.example.courseselectionbackend.querydsl;

import com.example.courseselectionbackend.model.*;
import com.example.courseselectionbackend.repository.CourseSelectionRepository;
import com.example.courseselectionbackend.repository.ProgramRepository;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.naming.Name;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Integer.min;

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
		return NamedTuple.toMapList(tuples, names);
	}

	public List<Map<String, Object>> findAllCourseClassInfoByCourseId(String courseId) {
		List<String> names = new ArrayList<String>(){{
			add("classid"); add("teacher"); add("time"); add("place"); add("capacity");
		}};

		List<Tuple> tuples = qf().select(courseClass.classId, teacher.teaName,
				courseClass.time, courseClass.place, courseClass.capacity)
				.from(courseClass, courseInfo, teacher)
				.where(courseInfo.courseId.eq(courseId), courseClass.courseId.eq(courseId),
						courseClass.teaId.eq(teacher.teaId))
				.fetch();

		return NamedTuple.toMapList(tuples, names, nt -> {
			int classId = (Integer)(nt.getObj("classid"));
			long allSelectNum = qf().select().from(courseClass, courseSelection)
					.where(courseClass.classId.eq(classId), courseSelection.id.classId.eq(classId))
					.fetchCount();
			int rest = (int)(nt.getObj("capacity"));
			int waiting = 0;
			if (allSelectNum > 0) {
				long haveSelectedNum = qf().select().from(courseClass, courseSelection)
						.where(courseClass.classId.eq(classId),
								courseSelection.id.classId.eq(classId), courseSelection.isOn.eq(true))
						.fetchCount();
				rest -= haveSelectedNum;
			}
			nt.add("rest", rest);
			nt.add("waiting", waiting);
		});
	}
}
