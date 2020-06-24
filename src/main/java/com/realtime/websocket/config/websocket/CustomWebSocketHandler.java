package com.realtime.websocket.config.websocket;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.*;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;

/**
 * @author: Staro
 * @date: 2020/6/19 16:20
 * @Description:
 */
@Component
public class CustomWebSocketHandler implements WebSocketHandler {

    private WebSocketSession socketSession;

    private  LoadingCache<String,Map<String,WebSocketSession>> webSocketSessionCache=CacheBuilder
            .newBuilder()
            .concurrencyLevel(60)   //允许并发更新数
            .initialCapacity(1)     //初始化容量
            .maximumSize(1)         //最大容量
            .recordStats()
            .removalListener(removalNotification -> System.out.println(removalNotification.getKey() + " was removed, cause is " + removalNotification.getCause()))
            .build(new CacheLoader<String, Map<String,WebSocketSession>>() {
                @Override
                public Map<String,WebSocketSession> load(String jspCode) throws Exception {
                    if (!StringUtils.isEmpty(jspCode)){
                        Map<String,WebSocketSession> map=new HashMap<>();
                        map.put(socketSession.getId(),socketSession);
                        return map;
                    }
                    return null;
                }
            });

    @Resource
    private ExecutorService executorService;

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        this.socketSession=webSocketSession;
        String jspCode = (String) webSocketSession.getAttributes().get("jspCode");
        if (jspCode != null) {
            Map<String, WebSocketSession> map = webSocketSessionCache.get(jspCode);
            map.putIfAbsent(webSocketSession.getId(), webSocketSession);
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
        ConcurrentMap<String, Map<String, WebSocketSession>> concurrentMap = webSocketSessionCache.asMap();
        Iterator<Map.Entry<String, Map<String, WebSocketSession>>> iterator = concurrentMap.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Map<String, WebSocketSession>> next = iterator.next();
            Map<String, WebSocketSession> value = next.getValue();
            value.remove(webSocketSession.getId());
        }
        System.out.println("connectionClose");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }


    public void sendMsgToJsp(final TextMessage message, String type) throws Exception {
        ConcurrentMap<String, Map<String, WebSocketSession>> concurrentMap = webSocketSessionCache.asMap();
        Map<String, WebSocketSession> map = concurrentMap.get(type);
        Iterator<Map.Entry<String, WebSocketSession>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, WebSocketSession> next = iterator.next();
            if (next.getValue().isOpen()){
                executorService.execute(()->{
                    try {
                        next.getValue().sendMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }
}
