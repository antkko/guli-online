package com.macro.vod;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author macro
 * @description 视频点播服务
 * @date 2023/12/26 17:43
 * @github https://github.com/bugstackss
 */
@ComponentScan(basePackages = {"com.macro"})
@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class VodApplication {
    public static void main(final String[] args) {
        SpringApplication.run(VodApplication.class, args);
    }
}
