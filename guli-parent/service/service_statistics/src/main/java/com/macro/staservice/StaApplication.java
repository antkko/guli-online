package com.macro.staservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author macro
 * @description statistics启动类
 * @date 2024/1/15 13:46
 * @github https://github.com/bugstackss
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.macro"})
@EnableFeignClients
@EnableDiscoveryClient
@MapperScan(basePackages = "com.macro.staservice.mapper")
@EnableScheduling // 开启定时任务
public class StaApplication {

    public static void main(final String[] args) {
        SpringApplication.run(StaApplication.class, args);
    }
}
