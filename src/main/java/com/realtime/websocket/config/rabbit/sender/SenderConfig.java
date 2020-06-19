package com.realtime.websocket.config.rabbit.sender;

import com.realtime.websocket.config.rabbit.queue.QueueConfig;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author: Staro
 * @date: 2020/6/19 15:56
 * @Description:
 */
@Component
public class SenderConfig {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(String msg){
        CorrelationData correlationData=new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(QueueConfig.EXCHANGE,QueueConfig.QUEUE,msg,correlationData);
    }
}
