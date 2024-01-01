package com.macro.serviceedu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.macro.serviceedu.entity.EduChapter;
import com.macro.serviceedu.entity.chapter.ChapterVo;

import java.util.List;

/**
 * 课程 服务类
 *
 * @author macro
 * @since 2023-12-05
 */
public interface EduChapterService extends IService<EduChapter> {

    /**
     * 课程大纲列表(根据id查询)
     *
     * @param courseId 课程id
     * @return 所有章节数据
     */
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    /**
     * 删除章节的方法
     *
     * @param chapterId 章节id
     */
    boolean deleteChapter(String chapterId);

    /**
     * 根据课程id删除章节
     *
     * @param courseId 课程id
     */
    void removeChapterByCourseId(String courseId);
}
