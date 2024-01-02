package com.macro.commonutils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * @author macro
 * @description 随机数工具类
 * @date 2024/1/2 17:15
 * @github https://github.com/bugstackss
 */
public class RandomUtil {

    private static final Random RANDOM = new Random();

    private static final DecimalFormat FOURDF = new DecimalFormat("0000");

    private static final DecimalFormat SIXDF = new DecimalFormat("000000");

    public static String getFourBitRandom() {
        return FOURDF.format(RANDOM.nextInt(10000));
    }

    public static String getSixBitRandom() {
        return SIXDF.format(RANDOM.nextInt(1000000));
    }

    /**
     * 给定数组，抽取n个数据
     *
     * @param list 数组
     * @param n    个数
     * @return 抽取的数据
     */
    public static ArrayList getRandom(final List list, final int n) {

        final Random random = new Random();

        final HashMap<Object, Object> hashMap = new HashMap<Object, Object>();

        // 生成随机数字并存入HashMap
        for (int i = 0; i < list.size(); i++) {

            final int number = random.nextInt(100) + 1;

            hashMap.put(number, i);
        }

        // 从HashMap导入数组
        final Object[] robjs = hashMap.values().toArray();

        final ArrayList r = new ArrayList();

        // 遍历数组并打印数据
        for (int i = 0; i < n; i++) {
            r.add(list.get((int) robjs[i]));
            System.out.print(list.get((int) robjs[i]) + "\t");
        }
        System.out.print("\n");
        return r;
    }
}
