package com.macro.staservice.client;

import com.macro.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author macro
 * @description
 * @date 2024/1/15 17:16
 * @github https://github.com/bugstackss
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {
     
    /**
     * 查询某一天的注册人数
     *
     * @param day 日期
     * @return 注册人数
     */
    @GetMapping("countRegister/{day}")
    R countRegister(@PathVariable("day") String day);
}
