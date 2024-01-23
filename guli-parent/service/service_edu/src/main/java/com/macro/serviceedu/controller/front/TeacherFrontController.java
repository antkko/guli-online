package com.macro.serviceedu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.macro.commonutils.R;
import com.macro.serviceedu.entity.EduCourse;
import com.macro.serviceedu.entity.EduTeacher;
import com.macro.serviceedu.service.EduCourseService;
import com.macro.serviceedu.service.EduTeacherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author macro
 * @description
 * @date 2024/1/2 20:10
 * @github https://github.com/bugstackss
 */
@RestController
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {

    @Resource
    private EduTeacherService teacherService;
    @Resource
    private EduCourseService courseService;

    /**
     * 分页查询讲师
     *
     * @param page  当前页
     * @param limit 每页记录数
     * @return 讲师列表
     */
    @GetMapping("getTeacherFrontList/{page}/{limit}")
    public R getTeacherFrontList(@PathVariable final long page, @PathVariable final long limit) {
        final Page<EduTeacher> pageTeacher = new Page<>(page, limit);
        final Map<String, Object> map = teacherService.getTeacherFrontList(pageTeacher);
        return R.ok().data(map);
    }

    /**
     * 讲师详情的功能
     *
     * @param teacherId 讲师id
     * @return 讲师详情
     */
    @GetMapping("getTeacherFrontInfo/{teacherId}")
    public R getTeacherFrontInfo(@PathVariable final String teacherId) {
        // 1 根据讲师id查询讲师基本信息
        final EduTeacher eduTeacher = teacherService.getById(teacherId);

        // 2 根据讲师id查询所讲课程
        final QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacherId);
        final List<EduCourse> courseList = courseService.list(wrapper);

        return R.ok().data("teacher", eduTeacher).data("courseList", courseList);
    }

}
