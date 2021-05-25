package com.jijie.jjv9index;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class JjV9IndexApplication {

    public static void main(String[] args) {
        SpringApplication.run(JjV9IndexApplication.class, args);
    }

}
