package com.macro.eduorder.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.macro.eduorder.entity.PayLog;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author macro
 * @since 2024-01-10
 */
public interface PayLogService extends IService<PayLog> {

    /**
     * 根据订单号生成微信支付二维码
     *
     * @param orderNo 订单号
     * @return 二维码地址
     */
    Map createNative(String orderNo);

    /**
     * 根据订单号查询订单支付状态
     *
     * @param orderNo 订单号
     * @return 订单支付状态
     */
    Map<String, String> queryPayStatus(String orderNo);

    /**
     * 添加记录到支付表，更新订单表订单状态
     *
     * @param map 支付结果
     */
    void updateOrderStatus(Map<String, String> map);
}
