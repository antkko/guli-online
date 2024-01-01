package com.macro.educms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macro.educms.entity.CrmBanner;
import com.macro.educms.entity.vo.BannerQuery;
import com.macro.educms.mapper.CrmBannerMapper;
import com.macro.educms.service.CrmBannerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author macro
 * @since 2023-12-28
 */
@Service
public class CrmBannerServiceImpl extends ServiceImpl<CrmBannerMapper, CrmBanner>
        implements CrmBannerService {

    @Cacheable(value = "banner", key = "'selectIndexList'")
    @Override
    public List<CrmBanner> selectAllBanner() {
        final QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc(StringUtils.isNotBlank("id"), "id").last("limit 2");
        return baseMapper.selectList(wrapper);
    }

    @Cacheable(value = "banner", key = "'pageBanner'")
    @Override
    public void pageQuery(final Page<CrmBanner> bannerPage, final BannerQuery bannerQuery) {
        // 创建QueryWrapper对象
        final QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();

        // 如果bannerQuery不为null，则添加查询条件
        if (Objects.nonNull(bannerQuery)) {
            final String name = bannerQuery.getName();
            final String begin = bannerQuery.getBegin();
            final String end = bannerQuery.getEnd();

            // 添加name相关的like条件
            if (!Objects.isNull(name) && !StringUtils.isBlank(name)) {
                wrapper.like("title", name);
            }
            // 添加gmt_create相关的ge条件
            if (!Objects.isNull(begin) && !StringUtils.isBlank(begin)) {
                wrapper.ge("gmt_create", begin);
            }
            // 添加gmt_modified相关的le条件
            if (!Objects.isNull(end) && !StringUtils.isBlank(end)) {
                wrapper.le("gmt_modified", end);
            }
        }

        // 排序
        wrapper.orderByDesc("gmt_create");

        // 执行分页查询
        baseMapper.selectPage(bannerPage, wrapper);
    }

}
