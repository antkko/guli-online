package com.macro;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

/**
 * @auther macro
 * @description
 * @date 2023/12/08 18:24
 *     <p>设计一个 Java 缓存中间件系统，该中间件的缓存写入后，经过指定时间缓存要自动失效。 在读取缓存时，如果缓存不存在，需要异步调用 AService.get(K)
 *     接口获取实时数据更新 到缓存中。
 */
public class CacheMiddleware<K, V> {

    private final Map<K, V> cacheMap;
    private final Map<K, Future<V>> futureMap;
    private final ExecutorService executorService;
    private final AService aService;
    private final long expirationTime; // 缓存过期时间，单位：毫秒

    public CacheMiddleware(final AService aService, final long expirationTime) {
        this.cacheMap = new HashMap<>();
        this.futureMap = new HashMap<>();
        this.executorService = Executors.newFixedThreadPool(10);
        this.aService = aService;
        this.expirationTime = expirationTime;
    }

    public V get(final K key) {
        // 有就返回
        if (this.cacheMap.containsKey(key)) {
            return this.cacheMap.get(key);
        }
        // 没有就异步更新
        final Future<V> future =
                this.executorService.submit(
                        () -> {
                            final V value = (V) this.aService.get(key);
                            this.cacheMap.put(key, value);
                            this.scheduleExpiration(key);
                            return value;
                        });
        this.futureMap.put(key, future);
        return null;
    }

    public void put(final K key, final V value) {
        if (this.futureMap.containsKey(key)) {
            final Future<V> future = this.futureMap.get(key);
            // 取消正在执行的任务
            if (!future.isDone()) {
                future.cancel(true);
            }
        }
        // 已经有的话 key 一样会覆盖
        this.cacheMap.put(key, value);
        this.scheduleExpiration(key);
    }

    public void remove(final K key) {
        if (this.cacheMap.containsKey(key)) {
            this.cacheMap.remove(key);
        }
    }

    public void update(final K key) {
        if (!this.futureMap.containsKey(key)) {
            return;
        }
        final Future<V> future = this.futureMap.get(key);
        if(future.isDone()){
            return;
        }
        try {
            future.get();
        } catch (final InterruptedException e) {
            e.printStackTrace();
        } catch (final ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void scheduleExpiration(final K key) {
        this.executorService.submit(
                () -> {
                    try {
                        Thread.sleep(this.expirationTime);
                        if (this.futureMap.containsKey(key)) {
                            final Future<V> future = this.futureMap.get(key);
                            if (!future.isDone()) {
                                future.cancel(true);
                            }
                        }
                        this.cacheMap.remove(key);
                        this.futureMap.remove(key);
                        System.out.println(key + " 过期,已删除!");
                    } catch (final InterruptedException e) {
                        e.printStackTrace();
                        System.out.println(key + " 过期,删除失败!");
                    }
                });
    }
}
