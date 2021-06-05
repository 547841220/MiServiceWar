package com.jijie.topic;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <p>Description: 消费者，去队列获取消息</p>
 *
 * @author jijie
 * @Date 2021/5/17 11:11
 */
public class MyConsumer2 {

    private static final String EXCHANGE_NAME = "topic_exchange";
    private static final String QUEUE_NAME = "topic_exchange-queue-2";

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

        //限流，最多只放行一个消息
        channel.basicQos(1);

        //声明队列和绑定交换机
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        //*只能识别一个单词,#能识别多个
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME,"ball.#");

        //3.创建一个消费者对象，并且写好处理消息的逻辑
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                String message = new String(body);
                System.out.println("消费者1我们接受到的消息是："+message);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //手工确认模式，告知MQ服务器，消息已经被正确处理
                //消息的标记envelope.getDeliveryTag()，
                //multiple:表示是否批量确认
                //为true,则批量确认，假设envelope.getDeliveryTag()=10,意味着将1-10的消息都确认回复。
                //false,只确认10
                channel.basicAck(envelope.getDeliveryTag(),false);

            }
        };
        //4.需要让消费者去监听队列
        //autoAck:是否自动确认，自动确认模式。false:需要手工确认
        //channel.basicConsume("simple-queue",true,consumer);
        //实际开发中建议使用false.手工确认模式()
        channel.basicConsume(QUEUE_NAME,false,consumer);



    }
}
