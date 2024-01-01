package com.macro.vod.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author macro
 * @description
 * @date 2023/12/26 17:47
 * @github https://github.com/bugstackss
 */
public interface VodService {

    /**
     * 上传视频到阿里云
     *
     * @param file 视频文件
     * @return 视频id
     */
    String uploadVideoAly(MultipartFile file);


    /**
     * 根据视频id删除多个阿里云视频
     *
     * @param videoIdList 视频id集合
     */
    void removeMoreAlyVideo(List<String> videoIdList);
}
