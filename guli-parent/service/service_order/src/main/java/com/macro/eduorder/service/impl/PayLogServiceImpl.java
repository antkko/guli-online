package com.macro.eduorder.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import com.macro.eduorder.entity.Order;
import com.macro.eduorder.entity.PayLog;
import com.macro.eduorder.mapper.PayLogMapper;
import com.macro.eduorder.service.OrderService;
import com.macro.eduorder.service.PayLogService;
import com.macro.eduorder.utils.HttpClient;
import com.macro.servicebase.exception.GuliException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author macro
 * @since 2024-01-10
 */
@Service
public class PayLogServiceImpl extends ServiceImpl<PayLogMapper, PayLog>
        implements PayLogService {

    @Resource
    private OrderService orderService;

    @Override
    public Map createNative(final String orderNo) {
        try {
            // 1. 根据订单号查询订单信息
            final QueryWrapper<Order> wrapper = new QueryWrapper<>();
            wrapper.eq("order_no", orderNo);
            final Order order = orderService.getOne(wrapper);

            // 2. 使用map设置生成二维码需要参数
            final Map m = new HashMap(13);
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("nonce_str", WXPayUtil.generateNonceStr());
            m.put("body", order.getCourseTitle());
            m.put("out_trade_no", orderNo);
            m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue() + "");
            m.put("spbill_create_ip", "127.0.0.1");
            m.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify");
            m.put("trade_type", "NATIVE");

            // 3. 使用HttpClient模拟浏览器提交请求,传递参数xml格式，微信支付提供的固定的地址
            final HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");

            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            // 执行请求发送
            client.post();

            // 4.得到发送请求返回结果
            // 返回的内容是xml格式
            final String xml = client.getContent();
            final Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            // 封装返回结果集
            final Map map = new HashMap<>(8);
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            map.put("result_code", resultMap.get("result_code"));// 返回二维码状态码
            map.put("code_url", resultMap.get("code_url"));// 二维码地址

            // 4. 得到返回结果
            return map;
        } catch (final Exception e) {
            throw new GuliException(20001, "生成二维码失败");
        }
    }

    @Override
    public Map<String, String> queryPayStatus(final String orderNo) {
        try {
            // 1、封装参数
            final Map m = new HashMap<>(8);
            m.put("appid", "wx74862e0dfcf69954");
            m.put("mch_id", "1558950191");
            m.put("out_trade_no", orderNo);
            m.put("nonce_str", WXPayUtil.generateNonceStr());

            // 2.设置请求
            final HttpClient client = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            client.setXmlParam(WXPayUtil.generateSignedXml(m, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();
            // 3、返回第三方的数据
            final String xml = client.getContent();

            return WXPayUtil.xmlToMap(xml);
        } catch (final Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void updateOrderStatus(final Map<String, String> map) {
        final String orderNo = map.get("out_trade_no");

        // 根据订单id查询订单信息
        final QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        final Order order = orderService.getOne(wrapper);
        final Integer status = order.getStatus();

        if (status == 1) {
            return;
        }

        order.setStatus(1);
        orderService.updateById(order);

        /* 记录支付日志 */
        final PayLog payLog = new PayLog();
        payLog.setOrderNo(orderNo);
        payLog.setPayTime(new Date());
        payLog.setPayType(1);// 支付类型
        payLog.setTotalFee(order.getTotalFee());// 总金额(分)
        payLog.setTradeState(map.get("trade_state"));// 支付状态
        payLog.setTransactionId(map.get("transaction_id"));
        payLog.setAttr(JSONObject.toJSONString(map));

        baseMapper.insert(payLog);
    }
}
