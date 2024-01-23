package com.macro.eduorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.macro.eduorder.entity.Order;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author macro
 * @since 2024-01-10
 */
public interface OrderService extends IService<Order> {

    /**
     * 根据课程id和用户id创建订单
     *
     * @param courseId           课程id
     * @param memberIdByJwtToken 获取用户id
     * @return 订单号
     */
    String createOrder(String courseId, String memberIdByJwtToken);

}
