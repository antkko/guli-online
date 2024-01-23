package com.macro.educenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.macro.educenter.entity.UcenterMember;
import com.macro.educenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author macro
 * @since 2024-01-02
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    /**
     * 登录
     *
     * @param member 用户信息
     * @return token
     */
    String login(UcenterMember member);

    /**
     * 注册
     *
     * @param registerVo 注册信息
     */
    void register(RegisterVo registerVo);

    /**
     * 根据openid判断是否有相同微信信息，进行微信登录
     *
     * @param openid 微信openid
     * @return 用户信息
     */
    UcenterMember getOpenIdMember(String openid);

    /**
     * 查询某一天的注册人数
     *
     * @param day 日期
     * @return 注册人数
     */
    Integer countRegisterDay(String day);

}
