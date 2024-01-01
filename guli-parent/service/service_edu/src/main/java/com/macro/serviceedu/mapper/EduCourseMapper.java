package com.macro.serviceedu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.macro.serviceedu.entity.EduCourse;
import com.macro.serviceedu.entity.vo.CoursePublishVo;

/**
 * 课程 Mapper 接口
 *
 * @author macro
 * @since 2023-12-05
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    public CoursePublishVo getPublishCourseInfo(String courseId);
}
