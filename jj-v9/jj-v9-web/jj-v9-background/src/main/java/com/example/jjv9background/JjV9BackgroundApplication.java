package com.example.jjv9background;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDubbo
@Import(FdfsClientConfig.class)
public class JjV9BackgroundApplication {

    public static void main(String[] args) {
        SpringApplication.run(JjV9BackgroundApplication.class, args);
    }

}
