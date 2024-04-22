//package com.example.mindmingle.controllers.config;
//
//import java.util.List;
//import java.util.Set;
//
//import com.example.mindmingle.entities.Groupe;
//import com.example.mindmingle.entities.Message;
//import com.example.mindmingle.services.IGroupe;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//@Component
//public class MyWebSocketHandler extends TextWebSocketHandler {
//
//    @Autowired
//    private IGroupe groupeService;
//
//    @Override
//    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        // Parse incoming message (e.g., groupId)
//        String payload = message.getPayload();
//        Integer groupId = Integer.parseInt(payload);
//
//        // Retrieve group messages (consider error handling)
//        Set<Groupe.MessageInfo> messages = groupeService.retrieveGroupMessages(groupId);
//        if (messages == null) {
//            // Handle case where messages are not found
//            session.sendMessage(new TextMessage("No messages found for group " + groupId));
//            return;
//        }
//
//        // Convert messages to JSON string
//        ObjectMapper mapper = new ObjectMapper();
//        String jsonMessages = mapper.writeValueAsString(messages);
//
//        // Send messages back to the client
//        session.sendMessage(new TextMessage(jsonMessages));
//    }
//}