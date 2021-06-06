package com.jijie.jjv9serarch.handler;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jijie.api.ISearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * <p>Description: </p>
 *
 * @author jijie
 * @Date 2021/5/17 11:11
 */
@Component
@RabbitListener(queues = "background-product-exchange-search-queue")
@Slf4j
public class BackgroundProductHandler {

    //1.声明队列 background-product-exchange-search-queue
    //2.绑定交换机
    //借助管理平台实现

    @Reference
    private ISearchService searchService;

    //RabbitHandler，具体处理
    @RabbitHandler
    public void process(Long newId) {
        log.info("处理了id为：{}的消息",newId);
        searchService.synById(newId);
    }

}
