package com.example.mindmingle.controllers;

import com.example.mindmingle.entities.Groupe;
import com.example.mindmingle.entities.Message;
import com.example.mindmingle.services.IMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("api/Message")
public class MessageController {
    IMessage messageService;

    @GetMapping("/getMessage/{idMsg}")
    public Message getMessage(@PathVariable("idMsg") Integer idMsg) {
        return messageService.retrieveMessage(idMsg);
    }
    @GetMapping("/getAllMessage")
    public List<Message> getAllMessage() {
        return messageService.retrieveAllMessage();
    }
    @PostMapping("/addMessage")
    public Message addMessage(@RequestBody Message message) {
        Message savedMessage = messageService.addMessage(message);
        return savedMessage;
    }

    @PutMapping("/updateMessage")
    public Message updateMessage(@RequestBody Message message){
        return messageService.upadateMessage(message);
    }
    @DeleteMapping("/deleteMessage/{idMsg}")
    public ResponseEntity<String>  removemessage(@PathVariable ("idMsg") Integer idMsg){
        messageService.removeMessage(idMsg);
        return ResponseEntity.status(HttpStatus.OK).body("Message deleted successfully.");
    }
    @GetMapping("/api/messages/{groupId}")
    public ResponseEntity<List<Message>> getMessagesByGroupId(@PathVariable int groupId) {
        List<Message> messages = messageService.findByGroupe_IdGroupe(groupId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

}
