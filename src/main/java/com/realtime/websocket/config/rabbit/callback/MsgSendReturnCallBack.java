package com.realtime.websocket.config.rabbit.callback;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author: Staro
 * @date: 2020/6/19 15:01
 * @Description:
 */
public class MsgSendReturnCallBack implements RabbitTemplate.ReturnCallback {
    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println("回馈信息:"+message);
    }
}
