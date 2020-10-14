package com.example.chatappdoodle.service;

import com.example.chatappdoodle.controller.MessageController;
import com.example.chatappdoodle.entity.MessageEntity;
import com.example.chatappdoodle.model.Message;
import com.example.chatappdoodle.repository.MessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageRepository messageRepository;

    public void save(Message message) {
        logger.info("Saving messages");
        messageRepository.save(new MessageEntity(message.getContent(), message.getSender()));
        logger.info("All messages " + findAll());
    }

    public List<MessageEntity> findAll() {
        return messageRepository.findAll();
    }
}
