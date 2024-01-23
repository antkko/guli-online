package com.macro.educenter.controller;


import com.macro.commonutils.JwtUtils;
import com.macro.commonutils.R;
import com.macro.commonutils.vo.UcenterMemberVo;
import com.macro.educenter.entity.UcenterMember;
import com.macro.educenter.entity.vo.RegisterVo;
import com.macro.educenter.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author macro
 * @since 2024-01-02
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {


    @Resource
    private UcenterMemberService memberService;

    /**
     * 登录
     *
     * @param member 用户信息
     * @return token
     */
    @PostMapping("login")
    public R loginUser(@RequestBody final UcenterMember member) {
        // 调用service方法实现登录
        // 返回token值，使用jwt生成
        final String token = memberService.login(member);
        return R.ok().data("token", token);
    }

    /**
     * 注册
     *
     * @param registerVo 注册信息
     * @return 是否成功
     */
    @PostMapping("register")
    public R registerUser(@RequestBody final RegisterVo registerVo) {
        memberService.register(registerVo);
        return R.ok();
    }

    /**
     * 根据token获取用户信息
     *
     * @param request 请求
     * @return 用户信息
     */
    @PostMapping("getMemberInfo")
    public R getMemberInfo(final HttpServletRequest request) {
        // 调用jwt工具类的方法，根据token字符串获取用户id
        final String memberId = JwtUtils.getMemberIdByJwtToken(request);
        // 查询数据库根据用户id获取用户信息
        final UcenterMember member = memberService.getById(memberId);
        return R.ok().data("userInfo", member);
    }

    /**
     * 根据用户id获取用户信息
     *
     * @param memberId 用户id
     * @return 用户信息
     */
    @PostMapping("getMemberInfoById/{memberId}")
    public UcenterMemberVo getMemberInfoById(@PathVariable final String memberId) {
        final UcenterMember member = memberService.getById(memberId);
        final UcenterMemberVo memberVo = new UcenterMemberVo();
        BeanUtils.copyProperties(member, memberVo);

        return memberVo;
    }

    /**
     * 根据日期统计注册人数
     *
     * @param day 日期
     * @return 注册人数
     */
    @GetMapping("countRegister/{day}")
    public R countRegister(@PathVariable final String day) {
        final Integer count = memberService.countRegisterDay(day);
        return R.ok().data("countRegister", count);
    }
}

