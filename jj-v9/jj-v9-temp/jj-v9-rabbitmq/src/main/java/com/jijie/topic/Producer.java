package com.jijie.topic;

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
    //发布消息的时候，给消息加标识用作识别。通配符模式
    private static final String EXCHANGE_NAME = "topic_exchange";

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
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");


        //发送10个消息

        //4.发送消息到交换机 football 消息打好了标记
        String message = "重大的足球消息，国足今晚只要打平就能出现";
        channel.basicPublish(EXCHANGE_NAME,"ball.football",null,message.getBytes());

        String message2 = "重大的蓝球消息，只要连续赢12场，我们就能出线";
        channel.basicPublish(EXCHANGE_NAME,"ball.mylove.basketball",null,message2.getBytes());


        System.out.println("发送消息成功");


    }
}
