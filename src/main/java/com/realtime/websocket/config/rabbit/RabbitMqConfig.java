package com.realtime.websocket.config.rabbit;


import com.realtime.websocket.config.rabbit.callback.MsgSendConfirmCallBack;
import com.realtime.websocket.config.rabbit.callback.MsgSendReturnCallBack;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: Staro
 * @date: 2020/6/19 14:24
 * @Description:
 */
@Configuration
public class RabbitMqConfig {
    private final ConnectionFactory connectionFactory;

    @Autowired
    public RabbitMqConfig(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Bean
    public MsgSendConfirmCallBack msgSendConfirmCallBack(){
        return new MsgSendConfirmCallBack();
    }

    @Bean
    public MsgSendReturnCallBack msgSendReturnCallBack(){
        return new MsgSendReturnCallBack();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(MsgSendConfirmCallBack msgSendConfirmCallBack,MsgSendReturnCallBack msgSendReturnCallBack){
        RabbitTemplate rabbitTemplate=new RabbitTemplate(connectionFactory);
        rabbitTemplate.setConfirmCallback(msgSendConfirmCallBack);
        rabbitTemplate.setReturnCallback(msgSendReturnCallBack);
        rabbitTemplate.setMandatory(true);
        return rabbitTemplate;
    }
}
