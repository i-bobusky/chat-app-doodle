package com.example.chatappdoodle.controller;

import com.example.chatappdoodle.model.Message;
import com.example.chatappdoodle.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;

@Controller
public class MessageController {

    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Resource
    private MessageService messageService;

    @Resource
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/sendMessage")
    public Message sendMessage(@Payload Message message) {
        logger.info("Sending messages");
        messageService.save(message);
        messagingTemplate.convertAndSend("/messageTopic", message);
        return message;
    }

    @GetMapping("/messages")
    public ResponseEntity<?> findAllMessages() {
        return ResponseEntity.ok(messageService.findAll());
    }

}
