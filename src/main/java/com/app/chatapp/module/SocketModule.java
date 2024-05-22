package com.app.chatapp.module;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.app.chatapp.model.Message;
import com.app.chatapp.service.SocketService;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SocketModule {
    private SocketIOServer socketIOServer;

    private SocketService socketService;

    private static final String WELCOME_MESSAGE = "%s bergabung dalam chat";
    private static final String DISCONNECT_MESSAGE = "%s meninggalkan chat";

    private ConnectListener onConnected() {
        return client -> {
            var params = client.getHandshakeData().getUrlParams();
            String chatRoom = params.get("chatRoom").stream().collect(Collectors.joining());
            String username = params.get("username").stream().collect(Collectors.joining());

            client.joinRoom(chatRoom);
            socketService.saveInfoMessage(client, String.format(WELCOME_MESSAGE, username), chatRoom);
            log.info("Socket ID[{}] - room[{}] - username [{}]  Connected to chat ", client.getSessionId().toString(),
                    chatRoom, username);
        };
    }

    private DisconnectListener onDisconnected() {
        return client -> {
            var params = client.getHandshakeData().getUrlParams();
            String chatRoom = params.get("chatRoom").stream().collect(Collectors.joining());
            String username = params.get("username").stream().collect(Collectors.joining());

            socketService.saveInfoMessage(client, String.format(DISCONNECT_MESSAGE, username), chatRoom);
            log.info("Socket ID[{}] - room[{}] - username [{}]  Disconnected from chat ",
                    client.getSessionId().toString(), chatRoom, username);
        };
    }

    private DataListener<Message> onChatReceived() {
        return (senderClient, data, ackSender) -> {
            log.info(data.toString());
            socketService.saveMessage(senderClient, data);
        };
    }

    public SocketModule(SocketIOServer server, SocketService socketService) {
        this.socketIOServer = server;
        this.socketService = socketService;

        socketIOServer.addConnectListener(onConnected());
        socketIOServer.addDisconnectListener(onDisconnected());
        socketIOServer.addEventListener("send_message", Message.class, onChatReceived());
    }
}
