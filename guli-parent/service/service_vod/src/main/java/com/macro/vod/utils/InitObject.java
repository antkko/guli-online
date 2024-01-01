package com.macro.vod.utils;

import com.aliyun.oss.ClientException;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.profile.DefaultProfile;

/**
 * @auther macro
 * @description
 * @date 2023/12/22 14:16
 */
public class InitObject {

    // 读取AccessKey信息
    public static DefaultAcsClient initVodClient(
            final String accessKeyId, final String accessKeySecret) throws ClientException {
        final String regionId = "cn-shanghai"; // 点播服务接入地域
        final DefaultProfile profile =
                DefaultProfile.getProfile(regionId, accessKeyId, accessKeySecret);
        return new DefaultAcsClient(profile);
    }

}
