package com.macro.msmservice.service;

import java.util.Map;

/**
 * @author macro
 * @description
 * @date 2024/1/2 13:51
 * @github https://github.com/bugstackss
 */
public interface MsmService {

    /**
     * 发送短信的方法
     *
     * @param param 参数
     * @param phone 手机号
     * @return 是否发送成功
     */
    Boolean send(Map<String, Object> param, String phone);
}
