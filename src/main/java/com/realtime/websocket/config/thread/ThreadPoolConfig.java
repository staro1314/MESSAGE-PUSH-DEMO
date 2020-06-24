package com.realtime.websocket.config.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.*;

/**
 * @author: Staro
 * @date: 2020/6/24 8:52
 * @Description:
 */
@Configuration
public class ThreadPoolConfig {

    @Bean
    public ExecutorService executorService() {
        ThreadFactory threadFactory = new ThreadFactoryBuilder()
                .setNameFormat("websocket-rabbitmq-%d")
                .build();

        return new ThreadPoolExecutor(5, 20, 0L, TimeUnit.MICROSECONDS,
                new LinkedBlockingQueue<Runnable>(100), threadFactory, new ThreadPoolExecutor.AbortPolicy());
    }
}
