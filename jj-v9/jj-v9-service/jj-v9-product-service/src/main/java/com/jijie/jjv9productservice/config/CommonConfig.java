package com.jijie.jjv9productservice.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * <p>Description: 通过注解的方式来代替之前xml方式。</p>
 *
 * @author jijie
 * @Date 2021/5/17 11:11
 */
@Configuration
public class CommonConfig {
    
    @Bean
    public PageHelper getPageHelper() {
        PageHelper pageHelper = new PageHelper();
        //设置属性
        Properties properties = new Properties();
        //
        properties.setProperty("dialect","mysql");
        //
        properties.setProperty("reasonable","true");
        pageHelper.setProperties(properties);
        return pageHelper;
    }
}
