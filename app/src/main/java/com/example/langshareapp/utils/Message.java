package com.example.langshareapp.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Message {
    private final int messageId;
    private final String senderId;
    private final String receiverId;
    private final String content;
    private final long timestamp;

    public Message() {
        this.messageId = 0;
        this.senderId = "";
        this.receiverId = "";
        this.content = "";
        this.timestamp = 0;
    }

    public Message(int messageId, String senderId, String receiverId, String message, long timestamp) {
        this.messageId = messageId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.content = message;
        this.timestamp = timestamp;
    }

    public String getSenderId() {
        return senderId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public String getContent() {
        return content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getMessageId() {
        return messageId;
    }

    public String getDateString() {
        Instant instant = Instant.ofEpochMilli(timestamp);
        LocalDateTime date = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return date.format(formatter);
    }
}
