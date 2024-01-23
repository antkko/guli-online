package com.macro.staservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.macro.staservice.entity.StatisticsDaily;

import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author macro
 * @since 2024-01-15
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    /**
     * 统计某一天的注册人数
     *
     * @param day 日期
     */
    void registerCount(String day);

    /**
     * 图表显示，返回两部分数据，日期json数组，数量json数组
     *
     * @param type  类型
     * @param begin 开始时间
     * @param end   结束时间
     * @return 是否成功
     */
    Map<String, Object> getShowData(String type, String begin, String end);
}
