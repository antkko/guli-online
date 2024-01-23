package com.macro.eduorder.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author macro
 * @description
 * @date 2024/1/10 18:36
 * @github https://github.com/bugstackss
 */
public class OrderNoUtil {

    /**
     * 获取订单号
     *
     * @return 订单号
     */
    public static String getOrderNo() {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        final String newDate = sdf.format(new Date());
        final String result;
        final Random random = new Random();
        result = IntStream.range(0, 3).mapToObj(i -> String.valueOf(random.nextInt(10))).collect(Collectors.joining());
        return newDate + result;
    }
}
