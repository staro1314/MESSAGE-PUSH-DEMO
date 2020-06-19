package com.realtime.websocket.config.rabbit.callback;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @author: Staro
 * @date: 2020/6/19 14:59
 * @Description:
 */
public class MsgSendConfirmCallBack implements RabbitTemplate.ConfirmCallback {
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        System.out.println("MsgSendConfirmCallBack id is "+correlationData);
        if (b){
            System.out.println("消息消费成功");
        }else {
            System.out.println("消息消费失败");
        }
    }
}
