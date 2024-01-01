package com.macro.serviceedu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.macro.serviceedu.entity.EduVideo;

/**
 * 课程视频 服务类
 *
 * @author macro
 * @since 2023-12-05
 */
public interface EduVideoService extends IService<EduVideo> {

    /**
     * 根据课程id删除小节
     *
     * @param courseId 课程id
     */
    void removeVideoByCourseId(String courseId);
}
