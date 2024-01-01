package com.macro;

/**
 * @auther macro
 * @description
 * @date 2023/12/08 20:41
 */
public class AServiceImpl implements AService {
    @Override
    public Integer get(final Object key) {
        // random 生成随机数
        return (int) (Math.random() * 10000);
    }
}
