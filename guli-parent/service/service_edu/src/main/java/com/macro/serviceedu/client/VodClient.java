package com.macro.serviceedu.client;

import com.macro.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author macro
 * @description
 * @date 2023/12/26 19:51
 * @github https://github.com/bugstackss
 */
@Component
@FeignClient(name = "service-vod", fallback = VodFileDegradeFeignClient.class)
public interface VodClient {

    /**
     * 根据视频id删除阿里云视频
     *
     * @param id 视频id
     * @return R 响应结果
     * @PathVariable 注解一定要指定参数名称，否则出错
     */
    @DeleteMapping("/eduvod/video/removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable("id") final String id);

    /**
     * 根据视频id删除多个阿里云视频
     *
     * @param videoIdList 视频id集合
     * @return R 响应结果
     */
    @DeleteMapping("/eduvod/video/delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") final List<String> videoIdList);
}
