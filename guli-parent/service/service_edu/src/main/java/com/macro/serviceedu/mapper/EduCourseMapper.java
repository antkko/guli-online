package com.macro.serviceedu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.macro.serviceedu.entity.EduCourse;
import com.macro.serviceedu.entity.frontvo.CourseWebVo;
import com.macro.serviceedu.entity.vo.CoursePublishVo;

/**
 * 课程 Mapper 接口
 *
 * @author macro
 * @since 2023-12-05
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    /**
     * 根据课程id查询课程确认信息
     *
     * @param courseId 课程id
     * @return 课程信息
     */
    CoursePublishVo getPublishCourseInfo(String courseId);

    /**
     * 根据课程id查询课程确认信息
     *
     * @param courseId 课程id
     * @return 课程信息
     */
    CourseWebVo getBaseCourseInfo(String courseId);
}
