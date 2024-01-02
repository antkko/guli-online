package com.macro.msmservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author macro
 * @description
 * @date 2024/1/2 13:49
 * @github https://github.com/bugstackss
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@ComponentScan({"com.macro"})
public class MsmApplication {
    public static void main(final String[] args) {
        SpringApplication.run(MsmApplication.class, args);
    }
}
