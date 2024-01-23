package com.macro.educenter;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author macro
 * @description
 * @date 2024/1/2 17:44
 * @github https://github.com/bugstackss
 */
@ComponentScan({"com.macro"})
@MapperScan("com.macro.educenter.mapper")
@EnableDiscoveryClient
@SpringBootApplication
public class UcenterApplication {

    public static void main(final String[] args) {
        SpringApplication.run(UcenterApplication.class, args);
    }
}
