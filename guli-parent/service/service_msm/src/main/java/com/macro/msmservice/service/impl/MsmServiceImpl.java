package com.macro.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.macro.msmservice.service.MsmService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author macro
 * @description
 * @date 2024/1/2 13:51
 * @github https://github.com/bugstackss
 */
@Service
@Slf4j
public class MsmServiceImpl implements MsmService {


    /**
     * 发送短信的方法
     *
     * @param param 参数
     * @param phone 手机号
     * @return 是否发送成功
     */
    @Override
    public Boolean send(final Map<String, Object> param, final String phone) {
        if (StringUtils.isEmpty(phone)) {
            return false;
        }

        final DefaultProfile profile =
                DefaultProfile.getProfile("default", "LTAI4GJ1Z9YQ4X3Zz9Q1XZ9Y", "1XZ9YQ4X3Zz9Q1XZ9YQ4X3Zz9Q1XZ9Y");
        final IAcsClient client = new DefaultAcsClient(profile);

        // 设置相关固定的参数
        final CommonRequest request = new CommonRequest();
        // request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2020-05-01");
        request.setAction("SendSms");

        // 设置发送相关的参数
        request.putQueryParameter("PhoneNumbers", phone); // 手机号
        request.putQueryParameter("SignName", "我的谷粒在线教育网站"); // 签名名称
        request.putQueryParameter("TemplateCode", "SMS_205891393"); // 模板code
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param)); // 验证码数据，转换json数据传递

        // 最终发送
        try {
            final CommonResponse response = client.getCommonResponse(request);
            final boolean success = response.getHttpResponse().isSuccess();
            log.info(response.getData());
            return success;
        } catch (final Exception e) {
            e.printStackTrace();
            return false;

        }
    }
}
