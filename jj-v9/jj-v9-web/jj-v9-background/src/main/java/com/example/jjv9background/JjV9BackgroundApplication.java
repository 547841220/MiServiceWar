package com.example.jjv9background;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class JjV9BackgroundApplication {

    public static void main(String[] args) {
        SpringApplication.run(JjV9BackgroundApplication.class, args);
    }

}
