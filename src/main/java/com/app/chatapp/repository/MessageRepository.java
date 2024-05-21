package com.app.chatapp.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.chatapp.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, UUID> {
    
}
