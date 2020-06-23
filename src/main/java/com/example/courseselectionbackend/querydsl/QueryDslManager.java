package com.example.courseselectionbackend.querydsl;

import com.example.courseselectionbackend.model.*;
import com.example.courseselectionbackend.repository.CourseSelectionRepository;
import com.example.courseselectionbackend.repository.ProgramRepository;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.CollectionExpression;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.hibernate.SQLQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.util.stream.Collectors;

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

	private String printTime(String[] ss) {
		String[] weekdays = {
			"一", "二", "三", "四", "五", "六", "日"
		};
		int[] times = {0, 2, 5, 8, 10};
		StringBuilder sb = new StringBuilder();
		sb.append("星期").append(weekdays[Integer.parseInt(ss[0])]);
		sb.append(" 第");
		int begin = times[Integer.parseInt(ss[1])];
		int end = begin + Integer.parseInt(ss[2]);
		for (int i = begin; i < end; i++) {
			if (i > begin)
				sb.append(",");
			sb.append(i + 1);
		}
		sb.append("节");
		return sb.toString();
	}

	public List<Map<String, Object>> findSelectedClassInfoByStuId(String stuId) {
		List<String> names = new ArrayList<String>(){{
			add("isOn"); add("cid"); add("tname"); add("time"); add("place"); add("cname");
		}};
		List<Tuple> tuples = qf().select(courseSelection.isOn, courseClass.courseId, teacher.teaName,
					courseClass.time, courseClass.place, courseInfo.courseName)
				.from(courseSelection, student, courseClass, teacher, courseInfo)
				.where(student.stuId.eq(stuId), student.stuId.eq(courseSelection.id.stuId), courseInfo.courseId.eq(courseClass.courseId),
						courseSelection.id.classId.eq(courseClass.classId), courseClass.teaId.eq(teacher.teaId))
				.fetch();
		return NamedTuple.toMapList(tuples, names, nt -> {
			String time = nt.getObj("time").toString();
			String[] times = time.split("_");
			StringBuilder sb = new StringBuilder();
			List<Map<String, Integer>> formattedTimes = Arrays.stream(times).map(t -> {
				String[] ss = t.split("\\|");
				sb.append(printTime(ss)).append("\n");
				return new HashMap<String, Integer>() {{
					put("weekday", Integer.valueOf(ss[0]));
					put("begin", Integer.valueOf(ss[1]));
					put("duration", Integer.valueOf(ss[2]));
				}};
			}).collect(Collectors.toList());
			sb.replace(sb.length()-1, sb.length(), "");
			nt.replace("time", formattedTimes);
			nt.add("printTime", sb.toString());
		});
	}

	public List<Map<String, Object>> findClassInfoByCourseId(String courseId, String stuId) {
		List<String> names = new ArrayList<String>(){{
			add("classid"); add("teacher"); add("courseTime"); add("coursePlace"); add("totalNum"); add("examTime");
		}};

		List<Tuple> tuples = qf().select(courseClass.classId, teacher.teaName,
				courseClass.time, courseClass.place, courseClass.capacity, courseClass.examTime)
				.from(courseClass, courseInfo, teacher)
				.where(courseInfo.courseId.eq(courseId), courseClass.courseId.eq(courseId),
						courseClass.teaId.eq(teacher.teaId))
				.fetch();
		return NamedTuple.toMapList(tuples, names, nt -> {
			int classId = (int)(nt.getObj("classid"));
			long allSelectNum = qf().select().from(courseClass, courseSelection)
					.where(courseClass.classId.eq(classId), courseSelection.id.classId.eq(classId))
					.fetchCount();
			long haveSelectedNum = 0;
			int capacity = (int)(nt.getObj("totalNum"));
			if (allSelectNum > 0) {
				haveSelectedNum = qf().select().from(courseClass, courseSelection)
						.where(courseClass.classId.eq(classId),
								courseSelection.id.classId.eq(classId), courseSelection.isOn.eq(true))
						.fetchCount();
			}
			long haveSelected = qf().select().from(courseClass)
					.join(courseSelection)
					.on(courseSelection.id.stuId.eq(stuId))
					.fetchCount();
			nt.add("remainNum", capacity - haveSelectedNum);
			nt.add("chosenNum", allSelectNum - haveSelectedNum);
			nt.add("chosen", haveSelected != 0);
			nt.remove("classid");

			String time = nt.getObj("courseTime").toString();
			String[] times = time.split("_");
			StringBuilder sb = new StringBuilder();
			for (String s: times) {
				sb.append(printTime(s.split("\\|"))).append("\n");
			}
			sb.replace(sb.length()-1, sb.length(), "");
			nt.replace("courseTime", sb.toString());
		});
	}

	public List<Map<String, Object>> findAllCoursesByConditions(String stuId, String courseId, String courseName, String tName, String cTime) {
		List<String> names = new ArrayList<String>(){{
			add("courseId"); add("courseName"); add("credits"); add("isOn");
		}};

		ArrayList<BooleanExpression> conds = new ArrayList<>();
		if (!courseId.isEmpty()) {
			conds.add(courseInfo.courseId.eq(courseId));
		}
		if (!courseName.isEmpty()) {
			conds.add(courseInfo.courseName.eq(courseName));
		}
		if (!tName.isEmpty()) {
			conds.add(teacher.teaName.eq(tName));
		}
		if (!cTime.isEmpty()) {
			conds.add(courseClass.time.eq(cTime));
		}
		List<Tuple> tuples;
		if (conds.isEmpty()) {
			tuples = qf()
					.select(courseInfo.courseId, courseInfo.courseName, courseInfo.courseCredits, courseSelection.isOn.castToNum(Integer.class).max())
					.from(courseInfo)
					.join(courseClass).on(courseClass.courseId.eq(courseInfo.courseId))
					.join(courseSelection).on(courseSelection.id.classId.eq(courseClass.classId), courseSelection.id.stuId.eq(stuId))
					.join(program).on(program.id.stuId.eq(stuId))
					.groupBy(courseInfo.courseId, courseInfo.courseName, courseInfo.courseCredits)
					.fetch();
		} else {
			BooleanExpression cond = conds.get(0);
			for (int i = 1; i < conds.size(); i++) {
				cond = cond.and(conds.get(i));
			}
			tuples = qf()
					.select(courseInfo.courseId, courseInfo.courseName, courseInfo.courseCredits, courseSelection.isOn.count())
					.from(courseInfo)
					.join(courseClass).on(courseClass.courseId.eq(courseInfo.courseId))
					.join(teacher).on(teacher.teaId.eq(courseClass.teaId))
					.leftJoin(courseSelection).on(courseSelection.id.classId.eq(courseClass.classId), courseSelection.id.stuId.eq(stuId))
					.where(cond)
					.groupBy(courseInfo.courseId, courseInfo.courseName, courseInfo.courseCredits)
					.fetch();
		}
		return NamedTuple.toMapList(tuples, names);
	}

}
