package com.realtime.websocket.config.rabbit.receive;

import com.realtime.websocket.config.rabbit.queue.QueueConfig;
import com.realtime.websocket.config.websocket.CustomWebSocketHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

/**
 * @author: Staro
 * @date: 2020/6/19 16:36
 * @Description:
 */
@Component
public class ReceiveConfig {
    @Autowired
    private CustomWebSocketHandler webSocketHandler;

    @RabbitListener(queues = "queue")
    public void receive(String msg)throws Exception{
        System.out.println(msg);
        webSocketHandler.sendMsgToJsp(new TextMessage(msg),QueueConfig.QUEUE);
    }
}
