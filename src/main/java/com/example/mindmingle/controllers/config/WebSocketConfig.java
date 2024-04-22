//package com.example.mindmingle.controllers.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.client.standard.StandardWebSocketClient;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
//import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
//
//@Configuration
//@EnableWebSocket
//public class WebSocketConfig implements WebSocketConfigurer {
//    @Bean
//    public StandardWebSocketClient webSocketClient() {
//        return new StandardWebSocketClient();
//    }
//    @Autowired
//    private MyWebSocketHandler webSocketHandler; // Use a specific handler
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(webSocketHandler, "/websocket-endpoint")
//                .setAllowedOrigins("*"); // Consider security implications of wildcards
//    }
//}