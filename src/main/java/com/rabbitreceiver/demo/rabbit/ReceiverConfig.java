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


    //监听方式 1
    @RabbitListener(bindings = {
            @QueueBinding(
                    value = @Queue(name = "dead_queue_1", durable = "true", autoDelete = "false"),
                    exchange = @Exchange(name = "dead_exchange_1",type = "x-delayed-message", durable = "true", autoDelete = "false"),
                    key = "dead_routing_key"
            )
        })
//    监听方式2
//    @RabbitListener(queues = "dead_queue_1")
    public void receive(@Payload String m){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(new Date()) + ":收到:" + m);
    }
}
