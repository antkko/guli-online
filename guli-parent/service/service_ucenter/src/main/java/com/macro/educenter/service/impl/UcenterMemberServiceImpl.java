package com.macro.educenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macro.commonutils.JwtUtils;
import com.macro.commonutils.MD5;
import com.macro.educenter.entity.UcenterMember;
import com.macro.educenter.entity.vo.RegisterVo;
import com.macro.educenter.mapper.UcenterMemberMapper;
import com.macro.educenter.service.UcenterMemberService;
import com.macro.servicebase.exception.GuliException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author macro
 * @since 2024-01-02
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember>
        implements UcenterMemberService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String login(final UcenterMember member) {
        // 获取登录手机号和密码
        final String mobile = member.getMobile();
        final String password = member.getPassword();
        // 手机号和密码非空判断
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "登录失败");
        }
        // 判断手机号是否正确
        final QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        final UcenterMember mobileMember = baseMapper.selectOne(wrapper);

        // 判断查询对象是否为空
        if (mobileMember == null) {
            throw new GuliException(20001, "登录失败");
        }
        // 判断密码
        // 因为存储到数据库里面的密码是加密的，所以这里要对输入的密码进行加密，再和数据库里面的密码进行比较
        // 这里使用MD5加密，但是MD5加密是不可逆的，所以这里使用MD5加密后的密码进行比较
        // 加密方式：MD5
        if (!MD5.encrypt(password).equals(mobileMember.getPassword())) {
            throw new GuliException(20001, "登录失败");
        }

        // 判断用户是否禁用
        if (mobileMember.getIsDisabled()) {
            throw new GuliException(20001, "登录失败");
        }

        // 登录成功
        // 生成token字符串，使用jwt工具类
        return JwtUtils.getJwtToken(mobileMember.getId(), mobileMember.getNickname());
    }

    /**
     * 注册
     *
     * @param registerVo 注册信息
     */
    @Override
    public void register(final RegisterVo registerVo) {
        // 获取注册信息
        final String code = registerVo.getCode();
        final String mobile = registerVo.getMobile();
        final String nickname = registerVo.getNickname();
        final String password = registerVo.getPassword();

        // 非空判断
        if (StringUtils.isAnyEmpty(mobile, password, nickname, code)) {
            throw new GuliException(20001, "注册失败");
        }

        // 判断验证码
        // 获取redis中的验证码
        final String redisCode = redisTemplate.opsForValue().get(mobile);
        if (!code.equals(redisCode)) {
            throw new GuliException(20001, "注册失败");
        }

        // 判断手机号是否重复，表里面存在相同手机号不进行添加
        final QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);
        final Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) {
            throw new GuliException(20001, "注册失败");
        }

        // 数据添加到数据库里面
        final UcenterMember member = new UcenterMember();
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
        member.setNickname(nickname);
        member.setIsDisabled(false);
        member.setAvatar("https://edu-macro-1300217907.cos.ap-chengdu.myqcloud.com/2020/01/02/0a9a3a7a-4b7e-4b1e-8f7e-9c5b5f0f7f7cfile.png");
        baseMapper.insert(member);

    }

    @Override
    public Integer countRegisterDay(final String day) {
        return baseMapper.countRegisterDay(day);
    }

    @Override
    public UcenterMember getOpenIdMember(final String openid) {
        final QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("openid", openid);
        return baseMapper.selectOne(wrapper);
    }
}
