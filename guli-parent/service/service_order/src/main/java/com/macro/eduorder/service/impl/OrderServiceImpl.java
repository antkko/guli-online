package com.macro.eduorder.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macro.commonutils.vo.EduCourseVo;
import com.macro.commonutils.vo.UcenterMemberVo;
import com.macro.eduorder.client.EduClient;
import com.macro.eduorder.client.UcenterClient;
import com.macro.eduorder.entity.Order;
import com.macro.eduorder.mapper.OrderMapper;
import com.macro.eduorder.service.OrderService;
import com.macro.eduorder.utils.OrderNoUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author macro
 * @since 2024-01-10
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private EduClient eduClient;

    @Resource
    private UcenterClient ucenterClient;

    /**
     * 根据课程id和用户id创建订单
     *
     * @param courseId 课程id
     * @param memberId 获取用户id
     * @return 订单号
     */
    @Override
    public String createOrder(final String courseId, final String memberId) {
        // 1.根据用户id获取用户信息
        final UcenterMemberVo memberInfo = ucenterClient.getMemberInfoById(memberId);

        // 2.根据课程id获取课程信息
        final EduCourseVo courseInfo = eduClient.getCourseInfoOrder(courseId);

        // 3.创建订单
        final Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo()); // 订单号
        order.setCourseId(courseId); // 课程id
        order.setMobile(memberInfo.getMobile()); // 手机号
        order.setNickname(memberInfo.getNickname()); // 昵称
        order.setMemberId(memberId); // 用户id
        order.setCourseCover(courseInfo.getCover()); // 课程封面
        order.setCourseTitle(courseInfo.getTitle()); // 课程标题
        order.setTeacherName(courseInfo.getTeacherName()); // 讲师姓名
        order.setTotalFee(courseInfo.getPrice()); // 课程价格
        order.setStatus(0);// 支付状态：（ 0：已支付，1：未支付 ）
        order.setPayType(1);// 支付类型： 1：微信 ， 2：支付宝

        // 保存订单
        baseMapper.insert(order);

        // 返回订单号
        return order.getOrderNo();

    }
}
