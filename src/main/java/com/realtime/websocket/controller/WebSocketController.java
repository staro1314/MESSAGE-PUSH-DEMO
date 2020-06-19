package com.realtime.websocket.controller;

import com.realtime.websocket.config.rabbit.sender.SenderConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author: Staro
 * @date: 2020/6/19 16:00
 * @Description:
 */
@Controller
public class WebSocketController {
    private final SenderConfig senderConfig;

    @Autowired
    public WebSocketController(SenderConfig senderConfig) {
        this.senderConfig = senderConfig;
    }

    @GetMapping("send")
    @ResponseBody
    public String send(String msg) {
        senderConfig.sendMsg(msg);
        return "success";
    }

    @GetMapping("index")
    public String index(){
        return "index";
    }
}
