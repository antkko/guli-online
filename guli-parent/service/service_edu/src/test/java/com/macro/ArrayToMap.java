package com.macro;

import java.util.HashMap;
import java.util.Map;

/**
 * @author macro
 * @description
 * @date 2023/12/29 12:57
 * @github https://github.com/bugstackss
 */
public class ArrayToMap {

    public static void main(final String[] args) {
        final int[] arr = {1, 2, 2, 3, 3, 3, 4, 5, 5};
        final Map<Integer, Integer> map = new HashMap<>();

        for (final int num : arr) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        System.out.println(map);
    }

    public static int findSingle(final int[] nums) {
        final Map<Integer, Integer> countMap = new HashMap<>();
        for (final int num : nums) {
            countMap.put(num, countMap.getOrDefault(num, 0) + 1);
        }

        for (final int num : nums) {
            if (countMap.get(num) == 1) {
                return num;
            }
        }

        return -1;
    }

//    public static void main(final String[] args) {
//        final ArrayToMap arrayToMap = new ArrayToMap();
//        final int[] nums = {4, 4, 3, 1, 2, 1, 2};
//        final int single = ArrayToMap.findSingle(nums);
//        System.out.println(single);
//    }
}
