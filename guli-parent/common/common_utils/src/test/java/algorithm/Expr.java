package algorithm;

import java.util.HashMap;

/**
 * @author macro
 * @description 算法题expr
 * @date 2024/1/21 16:03
 * @github https://github.com/bugstackss
 */
public class Expr {


    // 1.冒泡排序
    public static void bubbleSort(final int[] arr) {
        int temp;
        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }


    // 2.统计字符串出现3次以上的字符string
    public static void demo1(final String str) {
        final char[] chars = str.toCharArray();
        final HashMap<Character, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            final char aChar = chars[i];
            if (hashMap.containsKey(aChar)) {
                final Integer integer = hashMap.get(aChar);
                hashMap.put(aChar, integer + 1);
                if (integer + 1 > 3) {
                    System.out.println(aChar);
                }
            } else {
                hashMap.put(aChar, 1);
            }
        }
    }

    // 统计字符串出现3次以上的字符string
    public static String demo1_1(final String str) {
        final char[] chars = str.toCharArray();
        final HashMap<Character, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < str.length(); i++) {
            final char aChar = chars[i];
            if (hashMap.containsKey(aChar)) {
                final Integer integer = hashMap.get(aChar);
                hashMap.put(aChar, integer + 1);
                if (integer + 1 > 3) {
                    return String.valueOf(aChar);
                }
            } else {
                hashMap.put(aChar, 1);
            }
        }
        return null;
    }

    // 3.统计字符串出现的次数
    public static void demo2(final String str) {
        final int length = str.length();
        final char[] chars = str.toCharArray();
        final HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < length; i++) {
            final char aChar = chars[i];
            if (map.containsKey(aChar)) {
                final Integer integer = map.get(aChar);
                map.put(aChar, integer + 1);
            } else {
                map.put(aChar, 1);
            }
        }
    }

    // 4.  模拟一个trim方法，去除字符串两端的空格。
    public static String demo3(final String str) {
        int start = 0;
        int end = str.length() - 1;
        while (start < end && str.charAt(start) == ' ') {
            start++;
        }
        while (start < end && str.charAt(end) == ' ') {
            end--;
        }
        if (' ' == str.charAt(start)) {
            return "";
        }
        return str.substring(start, end + 1);
    }

    // 5.将一个字符串进行反转。将字符串中指定部分进行反转。比如“abcdefg”反 转为”abfedcg”
    public static String demo4(final String str, final int start, final int end) {
        final char[] chars = str.toCharArray();
        for (int i = start, j = end; i < j; i++, j--) {
            final char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
        return new String(chars);
    }

    // 5. 与上方相同,实现字符串反转
    public static String demo5(final String str) {
        final char[] chars = str.toCharArray();
        for (int i = 0, j = chars.length - 1; i < j; i++, j--) {
            final char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }

        return new String(chars);
    }

    // 6. 获取一个字符串在另一个字符串中出现的次数。 比如：获取“ ab”在 “abkkcadkabkebfkabkskab” 中出现的次数
    public static int getCount(String mainStr, final String subStr) {
        int count = 0;
        int index;
        while ((index = mainStr.indexOf(subStr)) != -1) {
            count++;
            mainStr = mainStr.substring(index + subStr.length());
        }

        return count;
    }

    // 7.获取两个字符串中最大相同子串。
    public static String demo6(final String str1, final String str2) {
        if (str1 != null && str2 != null) {
            final String maxStr = (str1.length() >= str2.length()) ? str1 : str2;
            final String minStr = (str1.length() < str2.length()) ? str1 : str2;
            final int length = minStr.length();
            for (int i = 0; i < length; i++) {
                for (int x = 0, y = length - 1; x < y; x++, y--) {
                    final String subStr = minStr.substring(x, y);
                    if (maxStr.contains(subStr)) {
                        return subStr;
                    }
                }
            }
        }
        return null;
    }

    // 8. 对字符串中字符进行自然顺序排序。 什么事自然顺序排序？ 就是按照数字的大小，按照字母的顺序进行排序
    public static void demo7(final String str) {
        final int length = str.length();
        final char[] chars = str.toCharArray();
        for (int i = 0; i < length - 1; i++) {

            for (int j = 0; j < length - i - 1; j++) {
                if (chars[j] > chars[j + 1]) {
                    final char temp = chars[j];
                    chars[j] = chars[j + 1];
                    chars[j + 1] = temp;
                }
            }
        }
    }

    // 9.判断一个字符串是否包含重复字符
    public static boolean demo8(final String str) {
        final char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            final char aChar = chars[i];
            for (int j = i + 1; j < chars.length; j++) {
                if (aChar != chars[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    // 10.是否为换位字符串
    public static boolean isSwapString(final String str1, final String str2) {
        if (str1.length() != str2.length()) {
            return false;
        }
        final char[] chars1 = str1.toCharArray();
        final char[] chars2 = str2.toCharArray();

        for (final char aChar : chars1) {
            for (int j = 0; j < chars2.length; j++) {
                if (aChar == chars2[j]) {
                    chars2[j] = ' ';
                    break;
                }
            }
        }

        for (final char c2 : chars2) {
            if (c2 == ' ') {
                return false;
            }
        }

        return true;
    }

    // 输入：nums = [2,7,11,15], target = 9
    // 输出：[0,1]
    // 解释：因为 nums[0] + nums[1] == 9 ，返回 [0, 1] 。
    public static int[] toSum(final int[] nums, final int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (target == nums[i] + nums[j]) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[0];
    }

    public static int[] toSumHash(final int[] nums, final int target) {
        final HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            final int a = nums[i];
            final int b = target - a;
            if (map.containsKey(b)) {
                return new int[]{map.get(b), i};
            }
        }
        return new int[0];
    }


}
