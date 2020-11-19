package com.rabbitreceiver.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Map;

//@Configuration
public class MyAckReceiver  implements ChannelAwareMessageListener {
    @Override
    public void onMessage(Message message, Channel channel) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try {

            String s = objectMapper.readValue(message.getBody(), String.class);


            //做一些业务处理
            if ("directQueue".equals(message.getMessageProperties().getConsumerQueue())){
                System.out.println(s);
                int i = 1/0;
                System.out.println("消费的消息来自的队列名为："+message.getMessageProperties().getConsumerQueue());
                System.out.println("执行fanout.A中的消息的业务处理流程......");

            }



            channel.basicAck(deliveryTag, true);

            Map<String, Object> headers = message.getMessageProperties().getHeaders();
            if (headers.containsKey("error")) {
                channel.basicNack(deliveryTag, false, true);
            }
        } catch (Exception e) {
            //channel.basicReject(deliveryTag, false);
            channel.basicNack(deliveryTag, false, true);
            e.printStackTrace();
        }


    }
}
