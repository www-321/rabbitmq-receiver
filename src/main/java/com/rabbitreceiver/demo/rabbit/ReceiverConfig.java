package com.rabbitreceiver.demo.rabbit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ReceiverConfig {


    @RabbitListener(queues = "dead_queue_1")
    public void receive(@Payload byte[] m) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.readValue(m, String.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date()) + "收到" + s);
    }
}
