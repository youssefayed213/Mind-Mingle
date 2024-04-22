package com.example.mindmingle.services;

import com.example.mindmingle.entities.CategorieGroupe;
import com.example.mindmingle.entities.Message;

import java.util.List;

public interface IMessage {
    public Message addMessage(Message message);
    public Message upadateMessage(Message message);
    void removeMessage(Integer idMsg);
    public Message retrieveMessage(Integer idMsg);
    public List<Message> retrieveAllMessage();

    List<Message> findByGroupe_IdGroupe(int groupId);
}
