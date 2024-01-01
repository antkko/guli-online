package com.macro.serviceedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author macro
 * @description
 * @date 2023/11/21 15:49
 */
@ComponentScan(basePackages = {"com.macro"})
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class EduApplication {
    public static void main(final String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
