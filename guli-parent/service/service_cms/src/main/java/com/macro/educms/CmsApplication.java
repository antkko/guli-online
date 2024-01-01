package com.macro.educms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @auther macro
 * @description cms启动类
 * @date 2023/11/21 15:26
 */

@MapperScan("com.macro.educms.mapper")
@ComponentScan(basePackages = {"com.macro"})
@SpringBootApplication
public class CmsApplication {
    public static void main(final String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }
}
