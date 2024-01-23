package com.macro.serviceedu.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author macro
 * @description
 * @date 2024/1/15 12:58
 * @github https://github.com/bugstackss
 */
@Component
@FeignClient("service-order")
public interface OrdersClient {

    /**
     * 根据课程id和用户id查询订单表中订单状态
     *
     * @param courseId 课程id
     * @param memberId 用户id
     * @return 订单状态
     */
    @PostMapping("/eduorder/order/isBuyCourse/{courseId}/{memberId}")
    boolean isBuyCourse(@PathVariable("courseId") final String courseId,
                        @PathVariable("memberId") final String memberId);
}
