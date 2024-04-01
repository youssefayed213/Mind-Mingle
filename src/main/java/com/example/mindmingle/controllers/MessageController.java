package com.example.mindmingle.controllers;

import com.example.mindmingle.entities.Groupe;
import com.example.mindmingle.entities.Message;
import com.example.mindmingle.services.IMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@AllArgsConstructor
@RequestMapping("api/Message")
public class MessageController {
    IMessage messageService; //injection des dépendances
    @GetMapping("/getMessage/{idMsg}")
    public Message getMessage(@PathVariable("idMsg") Integer idMsg) {
        return messageService.retrieveMessage(idMsg);
    }
    @GetMapping("/getAllMessage")
    public List<Message> getAllMessage() {
        return messageService.retrieveAllMessage();
    }
    @PostMapping("/addMessage")
    public  Message addMessage(@RequestBody Message message){
        return messageService.addMessage(message);
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

}
