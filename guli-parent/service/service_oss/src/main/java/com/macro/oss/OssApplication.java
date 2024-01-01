package com.macro.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;


/**
 * @auther macro
 * @description 阿里OSS服务模块
 * @date 2023/11/29 14:38
 */
@ComponentScan(basePackages = {"com.macro"})
@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class OssApplication {
    public static void main(final String[] args) {
        SpringApplication.run(OssApplication.class, args);
    }
}
