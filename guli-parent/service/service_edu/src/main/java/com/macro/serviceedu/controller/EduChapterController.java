package com.macro.serviceedu.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.macro.commonutils.R;
import com.macro.serviceedu.entity.EduChapter;
import com.macro.serviceedu.entity.chapter.ChapterVo;
import com.macro.serviceedu.service.EduChapterService;

/**
 * 课程 前端控制器
 *
 * @author macro
 * @since 2023-12-05
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/edu-chapter")
public class EduChapterController {

    @Resource
    private EduChapterService chapterService;

    /**
     * 课程大纲列表(根据id查询)
     *
     * @param courseId 课程id
     * @return R 响应结果
     */
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable final String courseId) {
        final List<ChapterVo> list = this.chapterService.getChapterVideoByCourseId(courseId);
        return R.ok().data("allChapterVideo", list);
    }

    @PostMapping("addChapter")
    public R addChapter(@RequestBody final EduChapter eduChapter) {
        this.chapterService.save(eduChapter);
        return R.ok();
    }

    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable final String chapterId) {
        final EduChapter eduChapter = this.chapterService.getById(chapterId);
        return R.ok().data("chapter", eduChapter);
    }

    @PostMapping("updateChapterInfo")
    public R updateChapterInfo(@RequestBody final EduChapter eduChapter) {
        this.chapterService.updateById(eduChapter);
        return R.ok();
    }

    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable final String chapterId) {
        final boolean flag = this.chapterService.deleteChapter(chapterId);
        if (flag) {
            return R.ok();
        } else {
            return R.error();
        }
    }
}
