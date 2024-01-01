package com.macro.serviceedu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macro.serviceedu.client.VodClient;
import com.macro.serviceedu.entity.EduVideo;
import com.macro.serviceedu.mapper.EduVideoMapper;
import com.macro.serviceedu.service.EduVideoService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 课程视频 服务实现类
 *
 * @author macro
 * @since 2023-12-05
 */
@Service
@RequiredArgsConstructor
public class EduVideoServiceImpl extends ServiceImpl<EduVideoMapper, EduVideo>
        implements EduVideoService {

    private final VodClient vodClient;

    /**
     * 根据课程ID删除视频
     *
     * @param courseId 课程ID
     */
    @Override
    public void removeVideoByCourseId(final String courseId) {
        // 1. 根据课程ID查询课程所有视频ID
        final LambdaQueryWrapper<EduVideo> wrapperVideo = new LambdaQueryWrapper<>();
        wrapperVideo.eq(ObjectUtils.isNotEmpty(courseId), EduVideo::getCourseId, courseId);
        wrapperVideo.select(EduVideo::getVideoSourceId);
        final List<EduVideo> eduVideoList = baseMapper.selectList(wrapperVideo);

        // List<EduVideo> 转 List<String>
        final List<String> videoIds = eduVideoList.stream()
                .map(EduVideo::getVideoSourceId)
                .filter(ObjectUtils::isNotEmpty)
                .collect(Collectors.toList());

        // 2. 根据视频ID删除阿里云视频
        if (!videoIds.isEmpty()) {
            vodClient.deleteBatch(videoIds);
        }

        final LambdaQueryWrapper<EduVideo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ObjectUtils.isNotEmpty(courseId), EduVideo::getCourseId, courseId);
        baseMapper.delete(wrapper);
    }
}
