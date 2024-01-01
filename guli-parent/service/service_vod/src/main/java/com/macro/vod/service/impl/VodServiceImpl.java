package com.macro.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.macro.servicebase.exception.GuliException;
import com.macro.vod.service.VodService;
import com.macro.vod.utils.ConstantVodUtils;
import com.macro.vod.utils.InitObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @author macro
 * @description
 * @date 2023/12/26 17:47
 * @github https://github.com/bugstackss
 */
@Slf4j
@Service
public class VodServiceImpl implements VodService {
    @SuppressWarnings("CallToPrintStackTrace")
    @Override
    public String uploadVideoAly(final MultipartFile file) {

        try {
            // accessKeyId, accessKeySecret
            // fileName: 上传文件原始名称
            final String fileName = file.getOriginalFilename();
            assert fileName != null;

            // title: 上传之后文件名称
            final String title = fileName.substring(0, fileName.lastIndexOf("."));

            // inputStream: 上传文件输入流
            final InputStream inputStream = file.getInputStream();

            final UploadStreamRequest request =
                    new UploadStreamRequest(
                            ConstantVodUtils.ACCESS_KEY_ID,
                            ConstantVodUtils.ACCESS_KEY_SECRET, title, fileName, inputStream);

            final UploadVideoImpl uploader = new UploadVideoImpl();
            final UploadStreamResponse response = uploader.uploadStream(request);

            final String videoId;

            if (response.isSuccess()) {
                videoId = response.getVideoId();
            } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
                videoId = response.getVideoId();
            }
            return videoId;
        } catch (final Exception e) {
            log.error("上传视频到阿里云出现错误:" + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void removeMoreAlyVideo(final List<String> videoIdList) {
        try {
            // 初始化对象
            final DefaultAcsClient client =
                    InitObject.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            // 创建删除视频request对象
            final DeleteVideoRequest request = new DeleteVideoRequest();
            // videoIdList值转换成1,2,3
            final String videoIds = StringUtils.join(videoIdList, ",");
            // 向request设置视频id
            request.setVideoIds(videoIds);
            // 调用初始化对象的方法实现删除
            client.getAcsResponse(request);
        } catch (final Exception e) {
            e.printStackTrace();
            throw new GuliException(20001, "删除视频失败");
        }
    }

}
