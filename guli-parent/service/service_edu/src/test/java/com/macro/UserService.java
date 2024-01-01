package com.macro;

/**
 * @auther macro
 * @description
 * @date 2023/12/08 18:45
 * <p>设计一个用户头像展示器，在 A 页面展示尺寸为 1*1 的头像，在 B 页面展示尺寸为 2*2 的 头像。 1）已有函数
 * UserService.getUserPicWithTargetSize(int height, int width)，可以返 回尺寸为 height * width 的头像 url；
 * 2）提供一个函数 UserService.getUserPicForPage(int pageSource)，返回页面对应尺寸 的头像 url; 3）不能用 if-else
 * 、三元表达式等条件控制语法来实现这个函数
 */
public class UserService {
    public static final int PAGE_SOURCE_A = 1;
    public static final int PAGE_SOURCE_B = 2;

    /**
     * 返回尺寸为 height * width 的头像 url 可直接使用
     */
    private static String getUserPicWithTargetSize(final int height, final int width) {
        return String.format("http://userpic_mock_h_%d_w_%d", height, width);
    }

    /**
     * 返回页面对应尺寸的头像 url
     */
    private static String getUserPicForPage(final int pageSource) {
        return getUserPicWithTargetSize(pageSource, pageSource);
    }

    public static void main(final String[] args) {
        final UserService userService = new UserService();
        System.out.println(UserService.getUserPicForPage(UserService.PAGE_SOURCE_A));
        System.out.println(UserService.getUserPicForPage(UserService.PAGE_SOURCE_B));
    }
}
