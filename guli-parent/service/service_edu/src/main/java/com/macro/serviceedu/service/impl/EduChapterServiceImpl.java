package com.macro.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macro.servicebase.exception.GuliException;
import com.macro.serviceedu.entity.EduChapter;
import com.macro.serviceedu.entity.EduVideo;
import com.macro.serviceedu.entity.chapter.ChapterVo;
import com.macro.serviceedu.entity.chapter.VideoVo;
import com.macro.serviceedu.mapper.EduChapterMapper;
import com.macro.serviceedu.service.EduChapterService;
import com.macro.serviceedu.service.EduVideoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 课程 服务实现类
 *
 * @author macro
 * @since 2023-12-05
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter>
        implements EduChapterService {

    @Resource
    private EduVideoService videoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(final String courseId) {
        // 1.根据课程id查询课程里面所有的章节
        final LambdaQueryWrapper<EduChapter> wrapperChapter = new LambdaQueryWrapper<>();
        wrapperChapter.eq(EduChapter::getCourseId, courseId);
        final List<EduChapter> eduChapterList = baseMapper.selectList(wrapperChapter);

        // 2.根据课程id查询课程里面所有的小节
        final LambdaQueryWrapper<EduVideo> wrapperVideo = new LambdaQueryWrapper<>();
        wrapperVideo.eq(StringUtils.isNotBlank(courseId), EduVideo::getCourseId, courseId);
        final List<EduVideo> eduVideoList = videoService.list(wrapperVideo);

        // 创建集合用于封装章节的小节
        final List<ChapterVo> finalList = new ArrayList<>();

        // 3.遍历所有章节list集合进行封装
        eduChapterList.forEach(
                eduChapter -> {
                    final ChapterVo chapterVo = new ChapterVo();
                    BeanUtils.copyProperties(eduChapter, chapterVo);
                    finalList.add(chapterVo);
                    final List<VideoVo> videoList = new ArrayList<>();
                    // 4.遍历所有小结list集合,进行封装, 判断小节的chapter_id和章节的id是否一致
                    eduVideoList.stream()
                            .filter(eduVideo -> eduVideo.getChapterId().equals(eduChapter.getId()))
                            .forEach(
                                    eduVideo -> {
                                        final VideoVo videoVo = new VideoVo();
                                        BeanUtils.copyProperties(eduVideo, videoVo);
                                        videoList.add(videoVo);
                                    });
                    chapterVo.setChildren(videoList);
                });

        return finalList;
    }

    @Override
    public boolean deleteChapter(final String chapterId) {
        // 根据chapter_id查询小节表,如果查询到数据,不进行删除
        final LambdaQueryWrapper<EduVideo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduVideo::getChapterId, chapterId);

        final int count = videoService.count(queryWrapper);
        if (count > 0) {
            throw new GuliException(20001, "请先删除小节");
        } else {
            // 根据chapter_id删除章节表
            final int delete = baseMapper.deleteById(chapterId);
            return delete > 0;
        }
    }

    @Override
    public void removeChapterByCourseId(final String courseId) {
        final LambdaQueryWrapper<EduChapter> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EduChapter::getCourseId, courseId);
        baseMapper.delete(queryWrapper);
    }
}
