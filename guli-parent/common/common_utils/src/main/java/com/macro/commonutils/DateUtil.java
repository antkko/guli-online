package com.macro.commonutils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author macro
 * @description 日期工具类
 * @date 2024/1/15 18:13
 * @github https://github.com/bugstackss
 */
public class DateUtil {

    private static final String DATE_FORMAT = "yyyy-MM-dd";

    /**
     * 格式化日期
     *
     * @param date 日期
     * @return 格式化后的日期
     */
    public static String formatDate(final Date date) {
        final SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date);

    }

    /**
     * 在日期date上增加amount天 。
     *
     * @param date   处理的日期，非null
     * @param amount 要加的天数，可能为负数
     */
    public static Date addDays(final Date date, final int amount) {
        final Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + amount);
        return now.getTime();
    }

    public static void main(final String[] args) {
        System.out.println(DateUtil.formatDate(new Date()));
        System.out.println(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
