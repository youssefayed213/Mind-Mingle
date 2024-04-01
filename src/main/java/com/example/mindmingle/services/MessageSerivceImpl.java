package com.example.mindmingle.services;

import com.example.mindmingle.entities.Message;
import com.example.mindmingle.repositories.CategorieGroupeRepository;
import com.example.mindmingle.repositories.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
public class MessageSerivceImpl implements IMessage{
    MessageRepository messageRepository;
    @Override
    public Message addMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Message upadateMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public void removeMessage(Integer idMsg) {
        messageRepository.deleteById(idMsg);
    }

    @Override
    public Message retrieveMessage(Integer idMsg) {
        return messageRepository.findById(idMsg).get();
    }

    @Override
    public List<Message> retrieveAllMessage() {
        return messageRepository.findAll();
    }
}
