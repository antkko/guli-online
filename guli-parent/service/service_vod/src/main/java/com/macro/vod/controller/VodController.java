package com.macro.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.macro.commonutils.R;
import com.macro.servicebase.exception.GuliException;
import com.macro.vod.service.VodService;
import com.macro.vod.utils.ConstantVodUtils;
import com.macro.vod.utils.InitObject;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author macro
 * @description
 * @date 2023/12/26 17:46
 * @github https://github.com/bugstackss
 */
@CrossOrigin
@RestController
@RequestMapping("/eduvod/video")
@RequiredArgsConstructor
public class VodController {

    private final VodService vodService;

    /**
     * 上传视频到阿里云
     *
     * @param file 视频文件
     * @return 视频id
     */
    @PostMapping("/uploadAlyVideo")
    public R uploadAlyVideo(final MultipartFile file) {
        /* 返回上传视频id */
        final String videoId = vodService.uploadVideoAly(file);
        return R.ok().data("videoId", videoId);
    }

    /**
     * 根据视频id删除阿里云视频
     *
     * @param id 视频id
     * @return R 响应结果
     */
    @DeleteMapping("removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable final String id) {
        try {
            // 初始化对象
            final DefaultAcsClient client =
                    InitObject.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            // 创建删除视频request对象
            final DeleteVideoRequest request = new DeleteVideoRequest();
            // 向request设置视频id
            request.setVideoIds(id);
            // 调用初始化对象的方法实现删除
            client.getAcsResponse(request);
            return R.ok();
        } catch (final Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "删除视频失败");
        }
    }

    /**
     * 删除多个阿里云视频的方法
     *
     * @param videoIdList 视频id集合
     * @return R 响应结果
     */
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") final List<String> videoIdList) {
        vodService.removeMoreAlyVideo(videoIdList);
        return R.ok();
    }

    /**
     * 根据视频id获取视频凭证
     *
     * @param id 视频id
     * @return 视频凭证
     */
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable final String id) {

        try {
            // 初始化对象
            final DefaultAcsClient client = InitObject.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            // 创建获取凭证request和response对象
            final GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            // 向request设置视频id
            request.setVideoId(id);
            // 调用方法得到凭证
            final GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            // 获取凭证
            final String playAuth = response.getPlayAuth();
            return R.ok().data("playAuth", playAuth);
        } catch (final ClientException e) {
            throw new GuliException(20001, "获取凭证失败");
        }
    }
}
