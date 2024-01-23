package algorithm;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

/**
 * @author macro
 * @description 统计字符串出现的次数
 * @date 2024/1/19 19:05
 * @github https://github.com/bugstackss
 */
public class StatsString {

    public static void main(final String[] args) {
        // final String str = "aabbccdd";
        // statsString(str);
        // statsStringCount(str);

        // final String str = "  a  b  c  ";
        // System.out.println(trim(str));
        //
        // final String str2 = "abcdefg";
        // System.out.println(reverse(str2, 2, 5));
        System.out.println(reverse("abcdefg"));
        System.out.println(sortString("sdfasf"));
    }

    /**
     * 统计字符串出现3次以上的字符string
     */
    public static void statsStringCount(final String str) {
        final int length = str.length();
        final HashMap<Character, Integer> hashMap = new HashMap<>();
        final char[] charArray = str.toCharArray();
        for (int i = 0; i < length; i++) {
            final char aChar = charArray[i];
            if (hashMap.containsKey(aChar)) {
                final Integer integer = hashMap.get(aChar);
                hashMap.put(aChar, integer + 1);
                if (integer + 1 >= 3) {
                    System.out.println(aChar);
                }
            } else {
                hashMap.put(aChar, 1);
            }
        }
        System.out.println(hashMap);
    }


    /**
     * 统计字符串出现的次数
     */
    public static void statsString(final String str) {
        final int length = str.length();
        final HashMap<Character, Integer> hashMap = new HashMap<>();
        final char[] charArray = str.toCharArray();
        for (int i = 0; i < length; i++) {
            final char aChar = charArray[i];
            if (hashMap.containsKey(aChar)) {
                final Integer integer = hashMap.get(aChar);
                hashMap.put(aChar, integer + 1);
            } else {
                hashMap.put(aChar, 1);
            }
        }
        System.out.println(hashMap);
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
        final TreeMap<Character, Integer> treeMap = new TreeMap<>();
        final Scanner scanner = new Scanner(System.in);
        System.out.println("请输入一个字符串");
        final String s = scanner.nextLine();
        for (int i = 0; i < s.length(); i++) {
            final char key = s.charAt(i);
            Integer value = treeMap.get(key);
            if (value == null) {
                treeMap.put(key, 1);
            } else {
                value++;
                treeMap.put(key, value);
            }
        }
        final StringBuilder builder = new StringBuilder();
        final Set<Character> keySet = treeMap.keySet();
        for (final Character key : keySet) {
            final Integer value = treeMap.get(key);
            builder.append(key).append("(").append(value).append(")");
        }
        final String string = builder.toString();
        System.out.println(string);
    }


    /*
     1. 模拟一个trim方法，去除字符串两端的空格。
     思路:
     01. 如果start == 0，说明字符串前面没有空格，直接返回字符串
     02. 如果start != 0，说明字符串前面有空格，直接返回从start开始到end结束的字符串
    */
    public static String trim(final String str) {
        final int length = str.length();
        int start = 0;
        int end = length - 1;
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

    /**
     * 2. 将一个字符串进行反转。将字符串中指定部分进行反转。比如“abcdefg”反 转为”abfedcg”
     */
    public static String reverseStr(final String str, final int start, final int end) {
        final char[] chars = str.toCharArray();

        for (int i = start, j = end; i < j; i++, j--) {
            final char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
        return new String(chars);
    }


    // 将字符串abcdefg反转成fedcba
    public static String reverse(final String str) {
        final char[] chars = str.toCharArray();
        for (int i = 0, j = chars.length - 1; i < j; i++, j--) {
            final char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
        return new String(chars);
    }

    // 3. 获取一个字符串在另一个字符串中出现的次数。 比如：获取“ ab”在 “abkkcadkabkebfkabkskab” 中出现的次数
    // 思路:
    // 1. indexOf(String str) 返回指定子字符串在此字符串中第一次出现处的索引。
    // 2. indexOf(String str, int fromIndex) 返回指定子字符串在此字符串中第一次出现处的索引，从指定的索引开始。
    // 3. 每次获取到indexOf找到的索引值，就让计数器++，然后让fromIndex = indexOf + str.length(),继续进行下一次的查找
    public static int getCount(String mainStr, final String subStr) {
        // 1.定义一个计数器，用来记录次数
        int count = 0;
        // 2.定义变量，记录indexOf方法返回的值
        int index;
        // 3.循环判断条件：子串在字符串中出现的位置
        while ((index = mainStr.indexOf(subStr)) != -1) {
            count++;
            // 4.子串在字符串中出现的位置之后的字符串，重新赋值给字符串
            mainStr = mainStr.substring(index + subStr.length());
        }
        return count;
    }


    // 4.获取两个字符串中最大相同子串。
    public static String getMaxSameString(final String str1, final String str2) {
        if (str1 != null && str2 != null) {
            final String maxStr = (str1.length() >= str2.length()) ? str1 : str2;
            final String minStr = (str1.length() < str2.length()) ? str1 : str2;
            final int length = minStr.length();
            for (int i = 0; i < length; i++) {
                for (int x = 0, y = length - 1; x < length - 1; x++, y--) {
                    final String subStr = minStr.substring(x, y);
                    if (maxStr.contains(subStr)) {
                        return subStr;
                    }
                }
            }

        }
        return null;
    }

    // 5.对字符串中字符进行自然顺序排序。 什么事自然顺序排序？ 就是按照数字的大小，按照字母的顺序进行排序
    public static String sortString(final String str) {
        final char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length - 1; i++) {
            for (int j = 0; j < chars.length - 1 - i; j++) {

                // 举例说明一下：如果是升序排序，那么就是前面的大于后面的，就交换位置
                // 如果是降序排序，那么就是前面的小于后面的，就交换位置
                // > 升序排序   < 降序排序
                if (chars[j] > chars[j + 1]) {
                    final char temp = chars[j];
                    chars[j] = chars[j + 1];
                    chars[j + 1] = temp;
                }

                // final char temp = chars[j];
                // chars[j] = chars[j + 1];
                // chars[j + 1] = temp;
            }
        }
        return new String(chars);
    }


    // 6.求一个字符串的所有排列

    // 7.判断一个字符串是否包含重复字符
    public static boolean isRepeat(final String str) {
        final char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            final char aChar = chars[i];
            for (int j = i + 1; j < chars.length; j++) {
                if (aChar == chars[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isRepeat2(final String str) {
        final char[] chars = str.toCharArray();
        for (final char aChar : chars) {
            if (str.indexOf(aChar) != str.lastIndexOf(aChar)) {
                return true;
            }
        }
        return false;
    }

    // 8.如何判断两个字符串是否为换位字符串
    // 换位字符串：指组成字符串的字符相同，但位置不同，如：“aaabbbccc”与“abcabcabc”
    public static boolean isSwapString(final String str1, final String str2) {
        if (str1.length() != str2.length()) {
            return false;
        }
        final char[] chars1 = str1.toCharArray();
        final char[] chars2 = str2.toCharArray();
        for (final char aChar : chars1) {
            for (int j = 0; j < chars2.length; j++) {
                if (aChar == chars2[j]) {
                    // 将找到的相同字符用空格代替
                    chars2[j] = ' ';
                    // 找到相同字符后，跳出内层循环，继续外层循环
                    break;
                }
            }
        }
        // 判断是否全部替换完成
        for (final char c : chars2) {
            // 如果没有全部替换完成，说明两个字符串不是换位字符串
            if (c != ' ') {
                return false;
            }
        }
        return true;
    }


}
