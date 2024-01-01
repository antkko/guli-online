package com.macro.serviceedu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.macro.commonutils.R;
import com.macro.serviceedu.entity.EduTeacher;
import com.macro.serviceedu.entity.vo.TeacherQuery;
import com.macro.serviceedu.service.EduTeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 讲师 前端控制器
 *
 * @author macro
 * @since 2023-11-21
 */
@Api(value = "讲师管理模块")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
@RequiredArgsConstructor
public class EduTeacherController {

    private final EduTeacherService teacherService;

    @ApiOperation(value = "查询教师表的所有记录")
    @GetMapping("findAll")
    public R findAllTeacher() {
        final List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items", list);
    }

    @ApiOperation(value = "根据id删除教师(逻辑删除)")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师id", required = true) @PathVariable final String id) {
        final boolean flag = teacherService.removeById(id);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @ApiOperation("分页查询讲师的方法")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable final Long current, @PathVariable final Long limit) {
        final Page<EduTeacher> teacherPage = new Page<>(current, limit);
        teacherService.page(teacherPage, null);

        final List<EduTeacher> records = teacherPage.getRecords();
        final long total = teacherPage.getTotal();

        return R.ok().data("total", total).data("rows", records);
    }

    /**
     * 使用@RequestBody必须是post请求,作用:前端传递json数据自动对应到实体类中!
     *
     * @param current      当前页码
     * @param limit        每页记录数
     * @param teacherQuery 查询参数
     * @return 带条件查询分页后的数据
     */
    @ApiOperation(value = "添加查询带分页")
    @PostMapping(value = "pageLimitCondition/{current}/{limit}")
    public R pageLimitCondition(
            @PathVariable final Long current,
            @PathVariable final Long limit,
            @RequestBody(required = false) final TeacherQuery teacherQuery) {
        final Page<EduTeacher> eduTeacherPage = new Page<>(current, limit);
        final QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();

        queryWrapper
                .like(
                        ObjectUtils.isNotEmpty(teacherQuery.getName()),
                        "name",
                        teacherQuery.getName())
                .eq(
                        ObjectUtils.isNotEmpty(teacherQuery.getLevel()),
                        "level",
                        teacherQuery.getLevel())
                .ge(
                        ObjectUtils.isNotEmpty(teacherQuery.getBegin()),
                        "gmt_create",
                        teacherQuery.getBegin())
                .le(
                        ObjectUtils.isNotEmpty(teacherQuery.getEnd()),
                        "gmt_modified",
                        teacherQuery.getEnd())
                .orderByDesc("gmt_create");

        teacherService.page(eduTeacherPage, queryWrapper);
        final List<EduTeacher> records = eduTeacherPage.getRecords();
        final long total = eduTeacherPage.getTotal();

        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "添加讲师接口")
    @PostMapping("addTeacher")
    public R addTeacher(@RequestBody final EduTeacher eduTeacher) {
        final boolean save = teacherService.save(eduTeacher);
        if (save) {
            return R.ok();
        } else {
            return R.error();
        }
    }

    @ApiOperation(value = "根据id查询讲师")
    @GetMapping(value = "getTeacher/{id}")
    public R getTeacher(@PathVariable final String id) {
        final EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher", eduTeacher);
    }

    @ApiOperation(value = "修改讲师")
    @PostMapping(value = "updateTeacher")
    public R updateTeacher(@RequestBody final EduTeacher eduTeacher) {
        final boolean flag = teacherService.updateById(eduTeacher);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}
