package com.macro.serviceedu.client;

import com.macro.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author macro
 * @description
 * @date 2023/12/27 17:41
 * @github https://github.com/bugstackss
 */
@Component
public class VodFileDegradeFeignClient implements VodClient {
    @Override
    public R removeAlyVideo(final String id) {
        return R.error().message("删除视频出错了");
    }

    @Override
    public R deleteBatch(final List<String> videoIdList) {
        return R.error().message("删除多个视频出错了");
    }
}
