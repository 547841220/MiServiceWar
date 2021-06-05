package com.jijie.jjv9item.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>Description: </p>
 *
 * @author jijie
 * @Date 2021/5/17 11:11
 */
@Configuration
public class CommonConfig {

    @Bean
    public ThreadPoolExecutor initThreadPoolExecutor() {
        //获取当前服务器的cpu核数
        int processors = Runtime.getRuntime().availableProcessors();
        //自定义的线程池 ---这里我们是每启一个线程，就创建一个线程池。这样不好。

        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                processors,processors*2,1L, TimeUnit.SECONDS,new LinkedBlockingQueue<>(100));
        return pool;
    }
}
