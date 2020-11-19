package com.rabbitreceiver.demo.rabbit;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ReceiverConfig {


    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(name = "dead_queue_1", durable = "true", autoDelete = "false"),
                    exchange = @Exchange(name = "dead_exchange_1",type = ExchangeTypes.DIRECT, durable = Exchange.TRUE, autoDelete = Exchange.FALSE),
                    key = "dead_routing_key"
            )
    })
    public void receive(@Payload byte[] m) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JSONObject object = objectMapper.readValue(m, JSONObject.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date()) + ":收到:" + object);
    }
}
