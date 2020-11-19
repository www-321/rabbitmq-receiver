package com.rabbitreceiver.demo.config;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.Payload;

import java.io.IOException;
@Configuration
public class AckDirectReceiver {

    private Logger logger = LoggerFactory.getLogger(AckDirectReceiver.class);

    /*
    deliveryTag（唯一标识 ID）：当一个消费者向 RabbitMQ 注册后，会建立起一个 Channel ，
    RabbitMQ 会用 basic.deliver 方法向消费者推送消息，这个方法携带了一个 delivery tag，
    它代表了 RabbitMQ 向该 Channel 投递的这条消息的唯一标识 ID，是一个单调递增的正整数，delivery tag 的范围仅限于 Channel
    multiple：为了减少网络流量，手动确认可以被批处理，当该参数为 true 时，则可以一次性确认 delivery_tag 小于等于传入值的所有消息
     */

    @RabbitListener(queues = "directQueue")
    public void ackReceive(Channel channel, Message message) throws IOException {
        System.out.println("收到：" + new String(message.getBody()));
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        System.out.println(deliveryTag);
        /**
         * 防止重复消费，可以根据传过来的唯一ID先判断缓存数据中是否有数据
         * 1、有数据则不消费，直接应答处理
         * 2、缓存没有数据，则进行消费处理数据，处理完后手动应答
         * 3、如果消息 处理异常则，可以存入数据库中，手动处理（可以增加短信和邮件提醒功能）
         */
        try {
           int i = 1/0;
            channel.basicAck(deliveryTag, true);
        } catch (Exception e) {
            if (message.getMessageProperties().getRedelivered()) {
                logger.error("重复处理失败，拒绝再次接受");

                channel.basicReject(deliveryTag, false);
            }else {
                logger.error("返回队列，再次处理");
                //第一个参数依然是当前消息到的数据的唯一id;
                //第二个参数是指是否针对多条消息；如果是true，也就是说一次性针对当前通道的消息的tagID小于当前这条消息的，都拒绝确认。
                //第三个参数是指是否重新入列，也就是指不确认的消息是否重新丢回到队列里面去。
                channel.basicNack(deliveryTag, false, true);
            }
            e.printStackTrace();
        }

    }
}
