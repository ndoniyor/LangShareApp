package com.example.langshareapp.utils;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom {
    private final String chatRoomId;
    private final List<String> participants;

    private final List<Message> messages;

    public ChatRoom(String chatRoomId, String user1, String user2) {
        this.chatRoomId = chatRoomId;
        this.participants = new ArrayList<>();
        this.messages = new ArrayList<>();
    }

    public String getChatRoomId() {
        return chatRoomId;
    }

    public void addUser(String user) {
        participants.add(user);
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public List<Message> getMessages() {
        return messages;
    }

    public List<String> getParticipants() {
        return participants;
    }
}
