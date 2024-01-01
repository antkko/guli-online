package com.macro.serviceedu.controller;

import com.macro.commonutils.R;
import com.macro.commonutils.ResultCode;
import com.macro.servicebase.exception.GuliException;
import com.macro.serviceedu.client.VodClient;
import com.macro.serviceedu.entity.EduVideo;
import com.macro.serviceedu.service.EduVideoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 课程视频 前端控制器
 *
 * @author macro
 * @since 2023-12-05
 */
@CrossOrigin
@RestController
@RequestMapping("/eduservice/video")
public class EduVideoController {

    @Resource
    private EduVideoService videoService;
    @Resource
    private VodClient vodClient;

    /**
     * 添加视频
     *
     * @param eduVideo 教育视频实体对象
     * @return R 返回结果对象
     */
    @PostMapping("addVideo")
    public R addVideo(@RequestBody final EduVideo eduVideo) {
        this.videoService.save(eduVideo);
        return R.ok();
    }

    /**
     * 删除视频
     *
     * @param id 视频ID
     * @return 删除结果
     */
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable final String id) {
        // 根据小节id获取视频id，调用方法实现视频删除
        final EduVideo eduVideo = this.videoService.getById(id);
        final String videoSourceId = eduVideo.getVideoSourceId();

        if (!StringUtils.isEmpty(videoSourceId)) {
            // 根据视频id，远程调用实现视频删除
            final R result = vodClient.removeAlyVideo(videoSourceId);
            if (ResultCode.ERROR.equals(result.getCode())) {
                throw new GuliException(20001, "删除视频失败，熔断器...");
            }
        }
        this.videoService.removeById(id);
        return R.ok();
    }

    /**
     * 更新视频信息
     *
     * @param eduVideo 视频信息实体
     * @return 更新结果
     */
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody final EduVideo eduVideo) {
        this.videoService.updateById(eduVideo);
        return R.ok();
    }

    /**
     * 根据视频id获取视频信息
     *
     * @param id 视频id
     * @return 视频信息
     */
    @GetMapping("getVideo/{id}")
    public R getVideo(@PathVariable final String id) {
        final EduVideo eduVideo = this.videoService.getById(id);
        return R.ok().data("video", eduVideo);
    }
}
