package com.example.web_socket.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;

@Component
public class StringWebSocketHandler extends TextWebSocketHandler {

    private static Map<String, WebSocketSession> sessionMap = new HashMap<>();

    /**
     * 建立连接，
     * 客户端 websocket = new WebSocket("ws://localhost:8080/websocket")到这里
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessionMap.put(session.getPrincipal().getName(), session); //存储当前登录人 与 WebSocketSession
        super.afterConnectionEstablished(session);
        //发一条登录成功的消息给客户端
        session.sendMessage(new TextMessage("欢迎：" + session.getPrincipal().getName() + "登录成功,你是第" + (sessionMap.size()) + "位访客")); //p2p
    }

    /**
     * 发送消息
     * 客户端调用websocket.send()到这里
     *
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(message.getPayload());
        jsonObject.put("sender", session.getPrincipal().getName()); //添加一个发送人id，供客户端识别谁发来的消息
        String userId = jsonObject.get("receiver").toString(); //获取消息发给谁
        TextMessage sendMessage = new TextMessage(jsonObject.toJSONString());
        if (sessionMap.containsKey(userId)) {
            WebSocketSession ws = sessionMap.get(userId);
            if (ws.isOpen()) {
                sessionMap.get(userId).sendMessage(sendMessage);
            }
        }
    }


    /**
     * 关闭连接
     * 客户端通过 websocket.close()方法到这里
     *
     * @param session
     * @param status
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionMap.remove(session.getPrincipal().getName());
        super.afterConnectionClosed(session, status);
    }

    /**
     * 连接出错后
     *
     * @param session
     * @param exception
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (session.isOpen()) {
            session.close();
        }
        System.out.println("连接出错");
        sessionMap.remove(session.getPrincipal().getName());
    }

}