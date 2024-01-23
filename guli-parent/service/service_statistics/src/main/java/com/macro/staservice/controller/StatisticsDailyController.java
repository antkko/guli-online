package com.macro.staservice.controller;


import com.macro.commonutils.R;
import com.macro.staservice.service.StatisticsDailyService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author macro
 * @since 2024-01-15
 */
@RestController
@RequestMapping("/staservice/sta")
@CrossOrigin
public class StatisticsDailyController {

    @Resource
    private StatisticsDailyService staService;

    /**
     * 统计某一天的注册人数
     *
     * @param day 日期
     * @return 是否成功
     */
    @PostMapping("registerCount/{day}")
    public R registerCount(@PathVariable final String day) {
        staService.registerCount(day);
        return R.ok();
    }

    /**
     * 图表显示，返回两部分数据，日期json数组，数量json数组
     *
     * @param type  类型
     * @param begin 开始时间
     * @param end   结束时间
     * @return 是否成功
     */
    @GetMapping("showData/{type}/{begin}/{end}")
    public R showData(@PathVariable final String type, @PathVariable final String begin, @PathVariable final String end) {
        final Map<String, Object> map = staService.getShowData(type, begin, end);
        return R.ok().data(map);
    }

}

