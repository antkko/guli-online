package com.macro.serviceedu.entity.vo;

import lombok.Data;

/**
 * @auther macro
 * @description
 * @date 2023/12/18 19:32
 */
@Data
public class CoursePublishVo {

	/**
	 * 课程id
	 */
	private String id;

	/**
	 * 课程标题
	 */
	private String title;

	/**
	 * 课程封面
	 */
	private String cover;

	/**
	 * 课程顺序号
	 */
	private Integer lessonNum;

	/**
	 * 课程一级科目
	 */
	private String subjectLevelOne;

	/**
	 * 课程二级科目
	 */
	private String subjectLevelTwo;

	/**
	 * 课程教师姓名
	 */
	private String teacherName;

	/**
	 * 课程价格
	 */
	private String price;
}
