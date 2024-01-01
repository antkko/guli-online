package com.macro.educms.controller;


import com.macro.commonutils.R;
import com.macro.educms.entity.CrmBanner;
import com.macro.educms.service.CrmBannerService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * banner前台显示控制器
 * </p>
 *
 * @author macro
 * @since 2023-12-28
 */
@RestController
@CrossOrigin
@RequestMapping("/educms/bannerfront")
public class BannerFrontController {

    @Resource
    private CrmBannerService bannerService;

    /**
     * 查询所有banner
     *
     * @return banner列表
     */
    @GetMapping("getAllBanner")
    public R getAllBanner() {
        final List<CrmBanner> list = bannerService.selectAllBanner();
        return R.ok().data("list", list);
    }

}

