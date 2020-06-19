package com.realtime.websocket.config.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author: Staro
 * @date: 2020/6/19 16:20
 * @Description:
 */
@Component
public class CustomWebSocketHandler implements WebSocketHandler {

    private static final Map<String, WebSocketSession> userMap = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        String jspCode = (String) webSocketSession.getAttributes().get("jspCode");
        if (jspCode != null) {
            System.out.println(jspCode);
            userMap.put(jspCode, webSocketSession);
        }
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {

    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {

    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {

    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    public void sendMsgToJsp(final TextMessage message,String type)throws Exception{
        Iterator<Map.Entry<String, WebSocketSession>> iterator = userMap.entrySet().iterator();
        while (iterator.hasNext()){
            final Map.Entry<String, WebSocketSession> entry = iterator.next();
            System.out.println(entry.getValue().isOpen());
            System.out.println(entry.getKey().contains(type));
            if (entry.getValue().isOpen()&&entry.getKey().contains(type)){
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            entry.getValue().sendMessage(message);
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    }
}
