package com.macro.serviceedu.controller;

import com.macro.commonutils.R;
import com.macro.serviceedu.entity.subject.OneSubject;
import com.macro.serviceedu.service.EduSubjectService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.annotation.Resource;

/**
 * 课程科目 前端控制器
 *
 * @author macro
 * @since 2023-11-30
 */
@Api("课程分类管理")
@CrossOrigin
@RestController
@RequestMapping("/eduservice/subject")
public class EduSubjectController {

    @Resource private EduSubjectService subjectService;

    /**
     * 添加课程分类
     *
     * @param file 上传的文件
     */
    @PostMapping("addSubject")
    public R addSubject(final MultipartFile file) {
        subjectService.saveSubject(file, subjectService);
        return R.ok();
    }

    @ApiOperation("查询课程列表(树形展示)")
    @GetMapping("getAllSubject")
    public R getAllSubject() {
        final List<OneSubject> list = subjectService.getOneTwoSubject();
        return R.ok().data("list",list);
    }
}
