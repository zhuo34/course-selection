package com.example.courseselectionbackend.service;

import com.example.courseselectionbackend.model.CourseSelection;
import com.example.courseselectionbackend.model.Program;
import com.example.courseselectionbackend.model.primarykey.CourseSelectionPK;
import com.example.courseselectionbackend.querydsl.QueryDslManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class CourseSelectionService {

	@Autowired
	private QueryDslManager queryManager;

	public List<Map<String, Object>> getClassesOfCourse(String courseId, String stuId) {
		return queryManager.findClassInfoByCourseId(courseId, stuId);
	}

	public List<Map<String, Object>> getSelectedCourses(String stuId) {
		return queryManager.findSelectedClassInfoByStuId(stuId);
	}

	public List<Map<String, Object>> searchCourses(String stuId, String courseId, String courseName, String tName, String cTime, int type) {
		return queryManager.findAllCoursesByConditions(stuId, courseId, courseName, tName, cTime, type);
	}

	@Transactional
	public Map<String, Object> getPrograms(String stuId) {
		int programState = queryManager.findProgramState(stuId);
		if (programState == 0) {
			queryManager.insertCompulsoryCourses(stuId);
			queryManager.startProgram(stuId);
		}
		HashMap<String, Object> ret = new HashMap<>();
		ret.put("courses", queryManager.findAllProgramsOfStudent(stuId));
		ret.put("isFinished", programState == 2);
		ret.putAll(queryManager.findMajorRequirementByStuId(stuId));
		return ret;
	}

	public boolean isProgramFinished(String stuId) {
		return queryManager.findProgramState(stuId) == 2;
	}

	@Transactional
	public void submitProgram(Program.Request request) {
		queryManager.insertPrograms(request.getStuId(), request.getInsert());
		queryManager.deletePrograms(request.getStuId(), request.getDelete());
		if (request.isSubmit())
			queryManager.submitProgram(request.getStuId());
	}

	@Transactional
	public void selectClass(CourseSelectionPK id) {
		queryManager.saveSelection(CourseSelection.builder().id(id).build());
	}

	@Transactional
	public void deleteClass(CourseSelectionPK id) {
		queryManager.deleteSelection(CourseSelection.builder().id(id).build());
	}

	@Transactional
	public void selectClassAdmin(CourseSelectionPK id) {
		queryManager.saveSelection(CourseSelection.builder().id(id).isOn(true).build());
	}

	@Transactional
	public void filterAllSelection() {
		List<CourseSelection> selections = queryManager.getAllSelection();
		List<CourseSelection> subSelections = new ArrayList<>();
		int classId = -1;
		int selectedNum = 0;
		for (CourseSelection selection : selections) {
			int thisClassId = selection.getId().getClassId();
			if (thisClassId == classId) {
				if (selection.isOn())
					selectedNum++;
				else
					subSelections.add(selection);
			} else {
				filterOneClass(classId, selectedNum, subSelections);
				System.out.println(subSelections.size());
				if (selection.isOn())
					selectedNum = 1;
				else
					subSelections.add(selection);
				classId = thisClassId;
			}
		}
		filterOneClass(classId, selectedNum, subSelections);
	}

	private void filterOneClass(int classId, int selectedNum, List<CourseSelection> subSelections) {
		if (classId < 0) return;
		Random random= new Random();
		int capacity = queryManager.getClassCapacity(classId);
		int rest = capacity - selectedNum;
		if (subSelections.size() > rest) {
			List<CourseSelection> onSelections = new ArrayList<>();
			while (rest > 0) {
				int index = random.nextInt(subSelections.size());
				CourseSelection lucky = subSelections.get(index);
				lucky.setOn(true);
				onSelections.add(lucky);
				subSelections.remove(lucky);
				rest--;
			}
			queryManager.saveSelection(onSelections);
			queryManager.deleteSelection(subSelections);
		} else {
			for (CourseSelection lucky: subSelections) {
				lucky.setOn(true);
			}
			queryManager.saveSelection(subSelections);
		}
		subSelections.clear();
	}
}
