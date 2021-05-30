package com.jijie.jjv9serarch;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDubbo
public class JjV9SerarchApplication {

    public static void main(String[] args) {
        SpringApplication.run(JjV9SerarchApplication.class, args);
    }

}
