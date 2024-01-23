package com.macro.eduorder;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author macro
 * @description
 * @date 2024/1/10 17:48
 * @github https://github.com/bugstackss
 */
@ComponentScan(basePackages = {"com.macro"})
@MapperScan("com.macro.eduorder.mapper")
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class OrdersApplication {
    public static void main(final String[] args) {
        SpringApplication.run(OrdersApplication.class);
    }
}
