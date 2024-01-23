package com.macro.educenter.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.macro.educenter.entity.UcenterMember;

/**
 * <p>
 * 会员表 Mapper 接口
 * </p>
 *
 * @author macro
 * @since 2024-01-02
 */
public interface UcenterMemberMapper extends BaseMapper<UcenterMember> {

    /**
     * 查询某一天的注册人数
     *
     * @param day 日期
     * @return 注册人数
     */
    Integer countRegisterDay(String day);
}
