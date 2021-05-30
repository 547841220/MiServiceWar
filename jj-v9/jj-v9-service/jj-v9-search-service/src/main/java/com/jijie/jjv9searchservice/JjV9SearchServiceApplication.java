package com.jijie.jjv9searchservice;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
@EnableDubbo
@MapperScan("com.jj.mapper")
public class JjV9SearchServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JjV9SearchServiceApplication.class, args);
    }

}
