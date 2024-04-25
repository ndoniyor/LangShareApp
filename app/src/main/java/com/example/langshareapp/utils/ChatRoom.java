package com.example.langshareapp.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ChatRoom {
    private int lastMessageId;
    private String chatRoomId;
    private final List<String> participants = new ArrayList<>();
    private HashMap<String, Message> messages;

    public ChatRoom() {
    }

    public ChatRoom(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public String getChatRoomId() {
        return chatRoomId;
    }

    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public void setParticipants(List<String> participants) {
        this.participants.clear();
        this.participants.addAll(participants);
    }

    public void addUser(String user) {
        participants.add(user);
    }

    public void addMessage(Message message) {
        if (messages == null) {
            messages = new HashMap<>();
        }
        messages.put(String.valueOf(message.getMessageId()), message);
    }

    public HashMap<String, Message> getMessages() {
        return messages;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public int getLastMessageId() {
        return lastMessageId;
    }

    public void setLastMessageId(int lastMessageId) {
        this.lastMessageId = lastMessageId;
    }

    public void setMessages(HashMap<String, Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ChatRoom chatRoom = (ChatRoom) obj;
        return Objects.equals(this.chatRoomId, chatRoom.chatRoomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatRoomId);
    }
}
