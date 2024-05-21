package com.app.chatapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.chatapp.model.Message;
import com.app.chatapp.types.MessageType;
import com.corundumstudio.socketio.SocketIOClient;

@Service
public class SocketService {
    @Autowired
    private MessageService messageService;

    public void sendSocketMessage(SocketIOClient senderClient, Message message, String chatRoom) {
        for (SocketIOClient client : senderClient.getNamespace().getRoomOperations(chatRoom).getClients()){
            if (!client.getSessionId().equals(senderClient.getSessionId())){
                client.sendEvent("read_message", message);
            }
        }
    }

    public void saveMessage(SocketIOClient senderClient, Message message) {
        Message storedMessage = messageService.saveMessage(
            Message.builder()
                .messageType(MessageType.CLIENT)
                .chatRoom(message.getChatRoom())
                .chatMessage(message.getChatMessage())
                .username(message.getUsername())
            .build()
        );

        sendSocketMessage(senderClient, storedMessage, message.getChatRoom());
    }

    public void saveInfoMessage(SocketIOClient senderClient, String message, String chatRoom) {
        Message storedMessage = messageService.saveMessage(
            Message.builder()
                .messageType(MessageType.SERVER)
                .chatRoom(chatRoom)
                .chatMessage(message)
            .build()
        );

        sendSocketMessage(senderClient, storedMessage, chatRoom);
    }
}
