package com.macro.serviceedu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.macro.serviceedu.entity.EduCourse;
import com.macro.serviceedu.entity.frontvo.CourseFrontVo;
import com.macro.serviceedu.entity.frontvo.CourseWebVo;
import com.macro.serviceedu.entity.vo.CourseInfoVO;
import com.macro.serviceedu.entity.vo.CoursePublishVo;

import java.util.Map;

/**
 * 课程 服务类
 *
 * @author macro
 * @since 2023-12-05
 */
public interface EduCourseService extends IService<EduCourse> {

    /**
     * 添加课程基本信息
     *
     * @param courseInfoVO 课程基本信息
     * @return 课程id
     */
    String saveCourseInfo(CourseInfoVO courseInfoVO);

    /**
     * 根据课程id查询课程基本信息
     *
     * @param courseId 课程id
     * @return 课程信息
     */
    CourseInfoVO getCourseInfo(String courseId);

    /**
     * 修改课程信息
     *
     * @param courseInfoVO 课程基本信息
     */
    void updateCourseInfo(CourseInfoVO courseInfoVO);

    /**
     * 根据课程id查询课程确认信息
     *
     * @param id 课程id
     * @return 课程信息
     */
    CoursePublishVo publishCourseInfo(String id);

    /**
     * 根据课程id删除课程
     *
     * @param courseId 课程id
     */
    void removeCourse(String courseId);

    /**
     * 条件查询带分页查询课程
     *
     * @param pageCourse    分页对象
     * @param courseFrontVo 查询条件
     * @return 查询结果
     */
    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    /**
     * 根据课程id查询课程信息
     *
     * @param courseId 课程id
     * @return 查询结果
     */
    CourseWebVo getBaseCourseInfo(String courseId);
}
