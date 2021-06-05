package com.jijie.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * <p>Description: 生产者，负责发送消息</p>
 *
 * @author jijie
 * @Date 2021/5/17 11:11
 */
public class Producer {
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

        //3.声明队列
        //如果该队列不存在，则创建该队列，否则，不创建
        //参数说明：
        //@queue:队列名；
        //@durable:是否支持持久化；如果true,生产者发布一个消息后，如果没有被消费，当服务重启后，消息仍然存在。如果false,重启后，消息消失。
        //@exclusive:如果声明的队列simple-queue队列仅限于此连接，则为true.也就是说，该通道只服务于该连接。一般设置false
        channel.queueDeclare("simple-queue",false,false,false,null);

        //4.发送消息到队列
        String message = "消息队列是一个非常重要的中间件";
        //我们没有指定交换机
        //我们只是指定了一个队列，实际上是使用了默认交换机。
        channel.basicPublish("","simple-queue",null,message.getBytes());

        System.out.println("发送消息成功");


    }
}
