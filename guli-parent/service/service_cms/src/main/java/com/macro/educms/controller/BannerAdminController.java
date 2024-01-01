package com.macro.educms.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.macro.commonutils.R;
import com.macro.educms.entity.CrmBanner;
import com.macro.educms.entity.vo.BannerQuery;
import com.macro.educms.service.CrmBannerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 后台banner管理接口
 * </p>
 *
 * @author macro
 * @since 2023-12-28
 */
@RestController
@CrossOrigin
@RequestMapping("/educms/banneradmin")
public class BannerAdminController {

    @Resource
    private CrmBannerService bannerService;

    /**
     * 分页查询banner
     *
     * @param page  当前页
     * @param limit 每页记录数
     * @return banner列表
     */
    @PostMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable final Long page, @PathVariable final Long limit,
                        @RequestBody final BannerQuery bannerQuery) {
        final Page<CrmBanner> pageBanner = new Page<>(page, limit);
        bannerService.pageQuery(pageBanner, bannerQuery);
        final List<CrmBanner> records = pageBanner.getRecords();
        final long total = pageBanner.getTotal();
        return R.ok().data("total", total).data("items", records);
    }

    @PostMapping("addBanner")
    public R addBanner(@RequestBody final CrmBanner crmBanner) {
        bannerService.save(crmBanner);
        return R.ok();
    }

    @ApiOperation(value = "删除Banner")
    @DeleteMapping("remove/{id}")
    public R remove(@PathVariable final String id) {
        bannerService.removeById(id);
        return R.ok();
    }

    @ApiOperation(value = "修改Banner")
    @PostMapping("update")
    public R updateById(@RequestBody final CrmBanner banner) {
        bannerService.updateById(banner);
        return R.ok();
    }

    @ApiOperation(value = "根据id获取Banner")
    @GetMapping("get/{id}")
    public R getById(@PathVariable final String id) {
        final CrmBanner banner = bannerService.getById(id);
        return R.ok().data("item", banner);
    }

}

