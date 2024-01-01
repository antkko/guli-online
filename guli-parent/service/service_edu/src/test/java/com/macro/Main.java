package com.macro;

/**
 * @auther macro
 * @description
 * @date 2023/12/08 20:46
 */
public class Main {
    public static void main(final String[] args) throws InterruptedException {
        testCatch();
    }

    private static void testCatch() throws InterruptedException {
        final CacheMiddleware cacheMiddleware = new CacheMiddleware(new AServiceImpl(), 1500);
        // cacheMiddleware.get(1);
        // cacheMiddleware.update(1);
        // System.out.println(cacheMiddleware.get(1));
        // Thread.sleep(500);
        // System.out.println(cacheMiddleware.get(1));
        // Thread.sleep(1000);
        // System.out.println(cacheMiddleware.get(1));
        // Thread.sleep(500);
        // System.out.println(cacheMiddleware.get(1));
        cacheMiddleware.put(1, "abc");
        System.out.println(cacheMiddleware.get(1));
        Thread.sleep(1600);
        System.out.println(cacheMiddleware.get(1));
        cacheMiddleware.update(1);
        System.out.println(cacheMiddleware.get(1));
    }
}
