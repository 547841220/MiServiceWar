package com.jijie.publish;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <p>Description: 生产者，负责发送消息</p>
 *
 * @author jijie
 * @Date 2021/5/17 11:11
 */
public class Producer {
    //发布订阅的模式
    private static final String EXCHANGE_NAME = "fanout_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        //1.创建连接，连接mq服务器
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.109.132");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("java1907");
        connectionFactory.setUsername("java1907");
        connectionFactory.setPassword("123");
        Connection connection = connectionFactory.newConnection();

        //2.基于这个连接对象，创建对应的通道
        Channel channel = connection.createChannel();


        //3.声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");


        //发送10个消息
        for (int i = 0; i < 10; i++) {
            //4.发送消息到交换机
            String message = "这是第"+i+"遍说我爱你";
            channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
        }


        System.out.println("发送消息成功");


    }
}
