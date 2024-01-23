package com.macro.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author macro
 * @description gateway网关启动类
 * @date 2024/1/18 20:09
 * @github https://github.com/bugstackss
 */
@EnableDiscoveryClient
@SpringBootApplication
public class ApiGatewayApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ApiGatewayApplication.class);
    }
}
