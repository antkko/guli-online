package com.macro.staservice.schedule;

import com.macro.commonutils.DateUtil;
import com.macro.staservice.service.StatisticsDailyService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author macro
 * @description 定时任务
 * @date 2024/1/15 17:59
 * @github https://github.com/bugstackss
 */
@Component
public class ScheduleTask {

    @Resource
    private StatisticsDailyService statisticsDailyService;

    @Scheduled(cron = "0/5 * * * * ?")
    public void task1() {
        System.out.println("*********++++++++++++*****执行了");
    }

    /**
     * 在每天凌晨1点，把前一天数据进行数据查询添加
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void task2() {
        statisticsDailyService.registerCount(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
