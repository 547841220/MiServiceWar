package com.jijie.jjv9productservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
@MapperScan("com.jj.mapper")
public class JjV9ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JjV9ProductServiceApplication.class, args);
    }

}
