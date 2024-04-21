package com.example.langshareapp.utils;

import java.time.LocalDate;

public class Message {
    private final String senderId;
    private final String receiverId;
    private final String content;
    private final long timestamp;

    public Message(String senderId, String receiverId, String message, long timestamp) {
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

    public String getDateString(){
        LocalDate date = LocalDate.ofEpochDay(timestamp);
        return date.toString();
    }
}
