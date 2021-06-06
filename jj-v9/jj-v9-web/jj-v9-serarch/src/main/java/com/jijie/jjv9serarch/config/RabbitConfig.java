package com.jijie.jjv9serarch.config;

import com.jijie.v9.common.constant.MQConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
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

    //1.声明队列
    @Bean
    public Queue initProductSearchQueue(){
        Queue queue = new Queue(
                MQConstant.QUEUE.BACKGROUND_PRODUCT_EXCHANGE_SEARCH_QUEUE,true,false,false);
        return queue;
    }

    //2.绑定交换机
    @Bean
    public TopicExchange initProductExchange() {
        TopicExchange productExchange = new TopicExchange(
                MQConstant.EXCHANGE.BACKGROUND_PRODUCT_EXCHANGE,true,false);
        return productExchange;
    }

    @Bean
    public Binding bindProductSearchQueueToProductExchange(
            Queue initProductSearchQueue,TopicExchange initProductExchange) {
        return BindingBuilder.bind(initProductSearchQueue).to(initProductExchange).with("product.add");
    }

}
