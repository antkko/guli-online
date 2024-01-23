package com.macro.eduorder.client;

import com.macro.commonutils.vo.UcenterMemberVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author macro
 * @description
 * @date 2024/1/10 18:29
 * @github https://github.com/bugstackss
 */
@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    /**
     * 根据用户id获取用户信息
     *
     * @param memberId 用户id
     * @return 用户信息
     */
    @PostMapping("/educenter/member/getMemberInfoById/{memberId}")
    UcenterMemberVo getMemberInfoById(@PathVariable("memberId") final String memberId);
}
