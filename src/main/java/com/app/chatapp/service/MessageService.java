package com.app.chatapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.chatapp.model.Message;
import com.app.chatapp.repository.MessageRepository;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    public List<Message> getMessage(String chatRoom){
        return messageRepository.findAllByChatRoom(chatRoom);
    }

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }
}
