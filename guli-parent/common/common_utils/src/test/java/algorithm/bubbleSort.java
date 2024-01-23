package algorithm;

import java.util.Arrays;

/**
 * @author macro
 * @description
 * @date 2024/1/19 16:08
 * @github https://github.com/bugstackss
 */
public class bubbleSort {

    public static void main(final String[] args) {
        final int[] arr = {3, 9, -1, 10, -2};
        bubbleSort(arr);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 冒泡排序
     *
     * @param arr
     */
    public static void bubbleSort(final int[] arr) {
        int temp;
        // 外层循环，决定比较的轮数
        for (int i = 0; i < arr.length - 1; i++) {
            // 内层循环，决定每轮比较的次数
            for (int j = 0; j < arr.length - 1 - i; j++) {
                // 比较大小，交换位置
                if (arr[j] > arr[j + 1]) {
                    // 交换位置
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    // 将代码优化
    public static void bubbleSortOptimize(final int[] arr) {
        int temp = 0;
        // 外层循环，决定比较的轮数
        for (int i = 0; i < arr.length - 1; i++) {
            // 内层循环，决定每轮比较的次数
            for (int j = 0; j < arr.length - 1 - i; j++) {
                // 比较大小，交换位置
                if (arr[j] > arr[j + 1]) {
                    // 交换位置
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }

            System.out.println("第" + (i + 1) + "轮排序后的数组");
            System.out.println(Arrays.toString(arr));
        }
    }

    // zaiyouhua
    public static void bubbleSortOptimize2(final int[] arr) {
        int temp;
        // 外层循环，决定比较的轮数
        for (int i = 0; i < arr.length - 1; i++) {
            // 内层循环，决定每轮比较的次数
            boolean flag = false;
            for (int j = 0; j < arr.length - 1 - i; j++) {
                // 比较大小，交换位置
                if (arr[j] > arr[j + 1]) {
                    // 交换位置
                    flag = true;
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }

            System.out.println("第" + (i + 1) + "轮排序后的数组");
            System.out.println(Arrays.toString(arr));

            // 解释一下为什么要加这个判断
            // 如果在某一轮排序中，没有发生过交换，说明数组已经有序，就可以直接跳出循环
            if (!flag) {
                break;
            }
        }
    }
}
