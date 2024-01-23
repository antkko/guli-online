package algorithm;

import java.util.HashMap;

/**
 * @author macro
 * @description
 * @date 2024/1/19 16:28
 * @github https://github.com/bugstackss
 */
public class twoSum {

    // public static void main(final String[] args) {
    // final int[] nums = {11, 15, 2, 7};
    // final int target = 9;
    //
    // twoSum(nums, target);

    // twoSum(new int[]{11, 15, 2, 7}, 9);
    // }

    // 这个解法是输出两个数,并非下标
    // public static int[] twoSumValue(final int[] nums, final int target) {
    //     final int[] result = new int[2];
    //     for (int i = 0; i < nums.length; i++) {
    //         final int a = nums[i];
    //         for (int j = i + 1; j < nums.length; j++) {
    //             final int b = nums[j];
    //             if (a + b == target) {
    //                 result[0] = i;
    //                 result[1] = j;
    //                 return result;
    //             }
    //         }
    //     }
    //     return new int[0];
    // }

    // 返回下标的解法
    public static int[] twoSumIndex(final int[] nums, final int target) {
        for (int i = 0; i < nums.length; i++) {
            final int a = nums[i];
            // i + 1 是为了避免重复计算 a + b = target 的情况
            for (int j = i + 1; j < nums.length; j++) {
                final int b = nums[j];
                if (a + b == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];
    }


    // 输入：nums = [2,7,11,15], target = 9
    // 输出：[0,1]
    // 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
    // public static void twoSum(final int[] nums, final int target) {
    //     for (int i = 0; i < nums.length; i++) {
    //         final int a = nums[i];
    //         for (int j = i + 1; j < nums.length; j++) {
    //             final int b = nums[j];
    //             if (a + b == target) {
    //                 System.out.println("[" + i + "," + j + "]");
    //             }
    //         }
    //     }
    // }

    // 1. 首先创建一个HashMap，用来存放数组中的元素，key为数组中的元素，value为数组中元素的下标
    // 2. 遍历数组，获取数组中的元素a
    // 3. 计算目标值与a的差值b
    // 4. 判断HashMap中是否存在b的key，如果存在，说明数组中存在两个元素的和为目标值，直接返回两个元素的下标即可
    // 5. 如果不存在，说明数组中不存在两个元素的和为目标值，将a存入HashMap中，继续遍历
    // 6. 如果遍历结束，还没有找到符合条件的两个元素，说明数组中不存在两个元素的和为目标值，返回空数组即可
    public static int[] twoSumHashMap(final int[] nums, final int target) {

        final HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            final int a = nums[i];
            final int b = target - a;
            if (hashMap.containsKey(b)) {
                return new int[]{hashMap.get(b), i};
            }
            hashMap.put(a, i);
        }
        return new int[0];
    }

}
