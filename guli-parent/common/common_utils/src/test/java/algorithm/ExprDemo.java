package algorithm;

import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * @author macro
 * @description
 * @date 2024/1/22 15:58
 * @github https://github.com/bugstackss
 */
public class ExprDemo {

    // 冒泡排序
    public static void bubbleSort(final int[] arr) {
        final int length = arr.length;
        int temp;
        for (int i = 0; i < length - 1; i++) {
            for (int j = 0; j < length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

    // 统计字符串出现3次以上的字符string
    public static String statsStringCount(final String str) {
        final int length = str.length();
        final char[] chars = str.toCharArray();
        final HashMap<Character, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < length; i++) {
            final char aChar = chars[i];
            if (hashMap.containsKey(aChar)) {
                final Integer integer = hashMap.get(aChar);
                hashMap.put(aChar, integer + 1);

                if (integer + 1 >= 3) {
                    hashMap.put(aChar, 1);
                }
            } else {
                hashMap.put(aChar, 1);
            }
        }
        return null;
    }

    /**
     * 用集合实现统计任意字符串中字符出现的次数
     * 1.使用Scanner类获取一个字符串
     * 2.创建HashMap集合，如果追求统计字符的美观性，可以使用TreeMap
     * 3.遍历字符串得到每一个字符并将其作为TreeMap的键
     * 4.通过键去集合中找相对应的值，看返回值是什么？
     * - 返回null:说明该字符在集合中不存在，就将该字符为键，次数1为值进行存储
     * - 返回的不是null:说明该字符在集合中存在，就再次将该字符作为键，次数+1为值进行存储
     * 5.遍历集合
     */
    public static void statsStringTreeMap(final String str) {
        final Scanner scanner = new Scanner(System.in);
        final String nexted = scanner.nextLine();
        final TreeMap<Character, Integer> map = new TreeMap<>();
        for (int i = 0; i < nexted.length(); i++) {
            final char key = nexted.charAt(i);
            Integer value = map.get(key);
            if (value == null) {
                map.put(key, 1);
            } else {
                value++;
                map.put(key, value);
            }
        }
        final StringBuilder builder = new StringBuilder();
        for (final Character key : map.keySet()) {
            final Integer value = map.get(key);
            builder.append(key).append("(").append(value).append(")");
        }
        final String string = builder.toString();
        System.out.println(string);
    }

    // 模拟一个trim方法，去除字符串两端的空格。
    public static String trim(final String str) {
        int start = 0;
        int end = str.length() - 1;
        while (start < end && str.charAt(start) != ' ') {
            start++;
        }
        while (start < end && str.charAt(end) != ' ') {
            end--;
        }
        if (str.charAt(start) == ' ') {
            return "";
        }
        return str.substring(start, end + 1);
    }

    // 将一个字符串进行反转。将字符串中指定部分进行反转。比如“abcdefg”反 转为”abfedcg”
    public static String reverse(final String str, final int start, final int end) {
        final char[] chars = str.toCharArray();
        for (int i = start, j = end; i < j; i++, j--) {
            final char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
        return new String(chars);
    }

    // 将字符串abcdefg反转成fedcba
    public static String reverseStr(final String str) {
        final char[] chars = str.toCharArray();
        for (int i = 0, j = chars.length - 1; i < j; i++) {
            final char temp = chars[i];
            chars[j] = chars[i];
            chars[j] = temp;
        }
        return new String(chars);
    }

    // 获取一个字符串在另一个字符串中出现的次数。 比如：获取“ ab”在 “abkkcadkabkebfkabkskab” 中出现的次数
    public static int getCount(String mainStr, final String subStr) {
        int index;
        int count = 0;
        while ((index = mainStr.indexOf(subStr)) != -1) {
            count++;
            mainStr = mainStr.substring(index + subStr.length());
        }

        return count;
    }

    // 获取两个字符串中最大相同子串。
    public static String getMaxStr(final String str1, final String str2) {
        if (str1 == null || str2 == null) {
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

    // 判断一个字符串是否包含重复字符
    public static boolean isRepeat(final String str) {
        final char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            final char aChar = chars[i];
            for (int j = i + 1; j < chars.length; j++) {
                if (chars[j] == aChar) {
                    return true;
                }
            }
        }
        return false;
    }

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
