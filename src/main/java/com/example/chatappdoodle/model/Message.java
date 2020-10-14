package com.example.chatappdoodle.model;

import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private String sender;
    private String content;
    private Date timestamp;
}
