package com.macro.eduorder.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.macro.commonutils.JwtUtils;
import com.macro.commonutils.R;
import com.macro.eduorder.entity.Order;
import com.macro.eduorder.service.OrderService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author macro
 * @since 2024-01-10
 */
@RestController
@RequestMapping("/eduorder/order")
@CrossOrigin
public class OrderController {

    @Resource
    private OrderService orderService;

    /**
     * 根据课程id和用户id创建订单
     *
     * @param courseId 课程id
     * @param request  请求
     * @return 订单号
     */
    @PostMapping("createOrder/{courseId}")
    public R createOrder(@PathVariable final String courseId, final HttpServletRequest request) {
        // 创建订单，返回订单号
        final String orderNo = orderService.createOrder(courseId, JwtUtils.getMemberIdByJwtToken(request));
        return R.ok().data("orderId", orderNo);
    }

    /**
     * 根据订单号查询订单信息
     *
     * @param orderId 订单号
     * @return 订单信息
     */
    @PostMapping("getOrderInfo/{orderId}")
    public R getOrderInfo(@PathVariable final String orderId) {
        final QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderId);
        final Order order = orderService.getOne(wrapper);
        return R.ok().data("item", order);
    }

    /**
     * 根据课程id和用户id查询订单表中订单状态
     *
     * @param courseId 课程id
     * @param memberId 用户id
     * @return 订单状态
     */
    @PostMapping("isBuyCourse/{courseId}/{memberId}")
    public boolean isBuyCourse(@PathVariable final String courseId, @PathVariable final String memberId) {
        final QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.eq("member_id", memberId);
        wrapper.eq("status", 1);
        final int count = orderService.count(wrapper);
        return count > 0;
    }
}

