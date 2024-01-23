package com.macro.staservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macro.commonutils.R;
import com.macro.staservice.client.UcenterClient;
import com.macro.staservice.entity.StatisticsDaily;
import com.macro.staservice.mapper.StatisticsDailyMapper;
import com.macro.staservice.service.StatisticsDailyService;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author macro
 * @since 2024-01-15
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily>
        implements StatisticsDailyService {

    @Resource
    private UcenterClient ucenterClient;

    @Override
    public void registerCount(final String day) {

        // 添加记录之前先删除表相同日期的数据，然后再进行添加
        final QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);

        final R register = ucenterClient.countRegister(day);
        final Integer countRegister = (Integer) register.getData().get("countRegister");

        // 把获取数据添加到数据库，统计分析表里面
        final StatisticsDaily statisticsDaily = new StatisticsDaily()
                .setRegisterNum(countRegister) // 注册人数
                .setLoginNum(RandomUtils.nextInt(100, 200)) // 登录人数
                .setVideoViewNum(RandomUtils.nextInt(100, 200)) // 视频播放数
                .setCourseNum(RandomUtils.nextInt(100, 200)) // 课程数
                .setDateCalculated(day);// 统计日期

        baseMapper.insert(statisticsDaily);
    }

    @Override
    public Map<String, Object> getShowData(final String type, final String begin, final String end) {
        // 1、根据条件查询对应的数据
        final QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper();
        wrapper.between("date_calculated", begin, end);
        wrapper.select("date_calculated", type);
        final List<StatisticsDaily> staList = baseMapper.selectList(wrapper);

        // 2、因为返回有两部分数据，日期和日期对应数量，所有需要创建两个集合
        // 日期集合
        final List<String> dateCalculatedList = new ArrayList<>();
        // 数量集合
        final List<Integer> numDataList = new ArrayList<>();
        // 遍历查询所有list集合，进行封装
        for (final StatisticsDaily daily : staList) {
            // 封装日期集合
            dateCalculatedList.add(daily.getDateCalculated());
            // 封装数量集合
            switch (type) {
                case "login_num":
                    numDataList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    numDataList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    numDataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numDataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }

        // 把封装之后两个集合放到map里面返回
        final Map<String, Object> map = new HashMap<>(4);
        map.put("date_calculatedList", dateCalculatedList);
        map.put("numDataList", numDataList);
        return map;
    }
}
