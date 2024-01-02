package com.macro.msmservice.controller;

import com.macro.commonutils.R;
import com.macro.commonutils.RandomUtil;
import com.macro.msmservice.service.MsmService;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author macro
 * @description
 * @date 2024/1/2 13:50
 * @github https://github.com/bugstackss
 */
@RestController
@CrossOrigin
@RequestMapping("/edumsm/msm")
public class MsmController {

    @Resource
    private MsmService msmService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 发送短信的方法
     *
     * @param phone 手机号
     * @return R是否发送成功
     */
    @GetMapping("/send/{phone}")
    public R sendMsm(final @PathVariable String phone) {
        // 从redis获取验证码，如果获取到直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if (StringUtils.isNotEmpty(code)) {
            return R.ok();
        }

        code = RandomUtil.getFourBitRandom();
        final Map<String, Object> param = new HashMap<>(16);
        param.put("code", code);
        // 调用service发送短信的方法
        final Boolean isSend = msmService.send(param, phone);
        if (isSend) {
            // 发送成功，把发送成功验证码放到redis里面
            redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);
            return R.ok();
        } else {
            return R.error().message("短信发送失败");
        }
    }
}
