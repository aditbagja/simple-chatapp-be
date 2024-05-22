package com.app.chatapp.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import com.app.chatapp.types.MessageType;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @Column
    private String chatRoom;

    @Column
    private String username;

    @Column
    private String chatMessage;

    @Column
    @Builder.Default
    private Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
}
