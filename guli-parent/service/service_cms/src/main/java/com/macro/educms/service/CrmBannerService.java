package com.macro.educms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.macro.educms.entity.CrmBanner;
import com.macro.educms.entity.vo.BannerQuery;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author macro
 * @since 2023-12-28
 */
public interface CrmBannerService extends IService<CrmBanner> {

    /**
     * 查询所有banner
     *
     * @return banner列表
     */
    List<CrmBanner> selectAllBanner();


    /**
     * 分页查询banner
     *
     * @param bannerPage  分页对象
     * @param bannerQuery 查询条件
     */
    void pageQuery(Page<CrmBanner> bannerPage, BannerQuery bannerQuery);
}
