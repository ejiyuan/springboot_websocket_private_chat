package com.example.web_socket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

//实现接口来配置Websocket请求的路径和拦截器。
@Configuration
@EnableWebSocket
public class WebSocketConfig  implements WebSocketConfigurer {
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        //handler是webSocket的核心，配置入口
        //添加一个WebSocketHandler，配置websocket入口，客户端通过“ws//localhost/websocket”直接访问Handler核心类，进行socket的连接、接收、发送等操作
        registry.addHandler(new StringWebSocketHandler(), "/websocket").setAllowedOrigins("*");
    }
}
