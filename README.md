# Course Selection Backend

This is the backend of our course selection subsystem, supported by Spring Boot, Spring Data JAP, QueryDsl, etc.

## 向前端提供的服务

* [ ] searchResult
	+ 参数：以下参数列表中的两个 + （string 学生专业）
		+ string：课程名称
		+ string：课程代码（id）
		+ string：教师姓名
		+ string：课程类别（必修、选修、公开课）
		+ int：上课时间起始（某天的第几节课）
		+ int：上课时间结束（某天的第几节课）
		+ int：春夏秋冬学期
	+ 返回值：课程信息
		+ int：课程代码（id）
		+ string：课程名称
		+ int：课程学分
		+ bool：该学生是否已选课
* [x] myCourse
	+ 参数：int 学生id
	+ 返回值：已选课程信息
		+ bool：已选上or筛选中
		+ int：课程代码（id）
		+ string：教师姓名
		+ int：上课时间起始
		+ int：上课时间结束
		+ string：上课地点
* [x] courseDetails
	+ 参数：int 课程代码（id）
	+ 返回值：
		+ string：教师姓名
		+ int：上课时间起始
		+ int：上课时间结束
		+ string：考试时间
		+ int：余量
		+ int：容量
		+ int：待定人数
* [ ] program_myCourse
	+ 参数：int 学生id
	+ 返回值：
		+ int 课程代码
		+ string 课程名称
		+ int 学分
		+ bool 课程是否必修
		+ string 课程所属专业
