package com.macro.eduorder.controller;


import com.macro.commonutils.R;
import com.macro.eduorder.service.PayLogService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author macro
 * @since 2024-01-10
 */
@RestController
@RequestMapping("/eduorder/pay-log")
@CrossOrigin
public class PayLogController {

    @Resource
    private PayLogService payLogService;

    /**
     * 根据订单号生成微信支付二维码
     *
     * @param orderNo 订单号
     * @return 二维码地址
     */
    @GetMapping("createNative/{orderNo}")
    public R createNative(@PathVariable final String orderNo) {
        final Map map = payLogService.createNative(orderNo);
        return R.ok().data(map);
    }

    /**
     * 根据订单号查询订单支付状态
     *
     * @param orderNo 订单号
     * @return 订单支付状态
     */
    @GetMapping("queryPayStatus/{orderNo}")
    public R queryPayStatus(@PathVariable final String orderNo) {
        final Map<String, String> map = payLogService.queryPayStatus(orderNo);
        if (map == null) {
            return R.error().message("支付出错了");
        }
        if ("SUCCESS".equals(map.get("trade_state"))) {
            // 添加记录到支付表，更新订单表订单状态
            payLogService.updateOrderStatus(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");
    }
}

