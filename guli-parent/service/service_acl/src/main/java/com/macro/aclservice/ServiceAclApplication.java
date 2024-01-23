package com.macro.aclservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Component;

/**
 * @author macro
 * @description
 * @date 2024/1/18 21:56
 * @github https://github.com/bugstackss
 */
@EnableDiscoveryClient
@MapperScan("com.macro.aclservice.mapper")
@Component("com.macro")
@SpringBootApplication
public class ServiceAclApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ServiceAclApplication.class);
    }
}
