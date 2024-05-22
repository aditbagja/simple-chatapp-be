package com.app.chatapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.chatapp.model.Message;
import com.app.chatapp.service.MessageService;

@CrossOrigin
@RestController
@RequestMapping("/chat")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/{chatRoom}")
    public ResponseEntity<List<Message>> getMessage(@PathVariable String chatRoom) {
        return ResponseEntity.ok(messageService.getMessage(chatRoom));
    }
}
