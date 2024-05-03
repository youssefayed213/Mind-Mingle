package com.example.mindmingle.controllers.handler;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

public class ChatWebSocketHandler extends TextWebSocketHandler {

    private final Map<String, Map<String, WebSocketSession>> groupSessionsMap = new HashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String groupId = extractGroupId(session);
        if (groupId != null) {
            groupSessionsMap.putIfAbsent(groupId, new HashMap<>());
            groupSessionsMap.get(groupId).put(session.getId(), session);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String groupId = extractGroupId(session);
        if (groupId != null) {
            Map<String, WebSocketSession> groupSessions = groupSessionsMap.get(groupId);
            if (groupSessions != null) {
                for (WebSocketSession groupSession : groupSessions.values()) {
                    groupSession.sendMessage(message);
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String groupId = extractGroupId(session);
        if (groupId != null) {
            Map<String, WebSocketSession> groupSessions = groupSessionsMap.get(groupId);
            if (groupSessions != null) {
                groupSessions.remove(session.getId());
                if (groupSessions.isEmpty()) {
                    groupSessionsMap.remove(groupId);
                }
            }
        }
    }

    private String extractGroupId(WebSocketSession session) {
        String uri = session.getUri().toString();
        String[] parts = uri.split("/");
        if (parts.length > 0) {
            String lastPart = parts[parts.length - 1];
            if (lastPart.matches("\\d+")) { // Assuming group ID is numeric
                return lastPart;
            }
        }
        return null;
    }

}
