package com.example.jjv9background.config;

import com.jijie.v9.common.constant.MQConstant;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Description: </p>
 *
 * @author jijie
 * @Date 2021/5/17 11:11
 */
@Configuration
public class RabbitConfig {

    @Bean
    public TopicExchange initProductExchange() {
        TopicExchange productExchange = new TopicExchange(
                MQConstant.EXCHANGE.BACKGROUND_PRODUCT_EXCHANGE,true,false);
        return productExchange;
    }
}
