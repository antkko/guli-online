package com.macro.serviceedu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.macro.commonutils.R;
import com.macro.serviceedu.entity.EduCourse;
import com.macro.serviceedu.entity.vo.CourseInfoVO;
import com.macro.serviceedu.entity.vo.CoursePublishVo;
import com.macro.serviceedu.entity.vo.CourseQuery;
import com.macro.serviceedu.service.EduCourseService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin
@RestController
@RequestMapping("/eduservice/edu-course")
public class EduCourseController {

    @Resource
    private EduCourseService courseService;

    /**
     * 课程列表分页查询
     *
     * @param current 当前页
     * @param limit 每页记录数
     * @param courseQuery 查询条件
     * @return 课程列表
     */
    @PostMapping("/{current}/{limit}")
    public R getCourseList(@PathVariable final Long current, @PathVariable final Long limit,
        @RequestBody final CourseQuery courseQuery) {
        final Page<EduCourse> eduCoursePage = new Page<>(current, limit);
        final QueryWrapper<EduCourse> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(ObjectUtils.isNotEmpty(courseQuery.getTitle()), "title", courseQuery.getTitle())
            .eq(ObjectUtils.isNotEmpty(courseQuery.getStatus()), "status", courseQuery.getStatus())
            .orderByDesc("gmt_create");

        courseService.page(eduCoursePage, queryWrapper);
        final Long total = eduCoursePage.getTotal();
        final List<EduCourse> records = eduCoursePage.getRecords();

        return R.ok().data("rows", records).data("total", total);
    }

    @ApiOperation("添加课程基本信息的方法")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody final CourseInfoVO courseInfoVO) {
        // 返回添加之后课程id,方便后续添加大纲使用
        final String courseId = courseService.saveCourseInfo(courseInfoVO);
        return R.ok().data("courseId", courseId);
    }

    /**
     * 根据课程id查询课程基本信息
     *
     * @param courseId 课程id
     * @return 课程基本信息
     */
    @ApiOperation("根据课程id查询课程基本信息")
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable final String courseId) {
        final CourseInfoVO courseInfoVO = courseService.getCourseInfo(courseId);
        return R.ok().data("courseInfoVO", courseInfoVO);
    }

    /**
     * 修改课程信息
     *
     * @param courseInfoVO 课程信息
     * @return 修改结果
     */
    @ApiOperation("修改课程信息")
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody final CourseInfoVO courseInfoVO) {
        courseService.updateCourseInfo(courseInfoVO);
        return R.ok();
    }

    @ApiOperation("根据课程id查询课程确认信息")
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable final String id) {
        final CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.ok().data("courseInfoVO", coursePublishVo);
    }

    /**
     * 课程最终发布状态
     *
     * @param id 课程id
     * @return 发布是否成功
     */
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable final String id) {
        final EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return R.ok();
    }

    /**
     * 删除指定课程
     *
     * @param courseId 课程ID
     * @return 删除结果
     */
    @DeleteMapping("{courseId}")
    public R deleteCourse(@PathVariable final String courseId) {
        courseService.removeCourse(courseId);
        return R.ok();
    }
}
