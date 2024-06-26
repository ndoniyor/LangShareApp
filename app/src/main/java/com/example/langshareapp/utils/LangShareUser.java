package com.example.langshareapp.utils;

import com.google.firebase.firestore.DocumentSnapshot;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LangShareUser {
    private String authId;
    private String fullName;
    private String email;
    private String bio;
    private LocalDate dob;
    private LocalDate createdAt;
    private List<String> languagesLearning;
    private List<String> languagesKnown;
    private List<String> chatHistory;

    public LangShareUser(String authId, String fullName, String email, String bio, LocalDate dob, LocalDate createdAt, List<String> languagesLearning, List<String> languagesKnown, List<String> chatHistory) {
        this.authId = authId;
        this.fullName = fullName;
        this.email = email;
        this.bio = bio;
        this.dob = dob;
        this.createdAt = createdAt;
        this.languagesLearning = languagesLearning;
        this.languagesKnown = languagesKnown;
        this.chatHistory = chatHistory;
    }

    public static LangShareUser fromDocument(DocumentSnapshot document) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return new LangShareUser(
                document.getString("auth_id"),
                document.getString("full_name"),
                document.getString("email"),
                document.getString("bio"),
                LocalDate.parse(document.getString("dob")),
                LocalDate.parse(document.getString("created_at")),
                (List<String>) document.get("languages_learning"),
                (List<String>) document.get("languages_known"),
                (List<String>) document.get("chat_history")

        );
    }

    public static List<LangShareUser> fromDocumentList(List<DocumentSnapshot> documents) {
        List<LangShareUser> users = new ArrayList<>();
        for (DocumentSnapshot document : documents) {
            users.add(fromDocument(document));
        }
        return users;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public List<String> getLanguagesLearning() {
        return languagesLearning;
    }

    public void setLanguagesLearning(List<String> languagesLearning) {
        this.languagesLearning = languagesLearning;
    }

    public List<String> getLanguagesKnown() {
        return languagesKnown;
    }

    public void setLanguagesKnown(List<String> languagesKnown) {
        this.languagesKnown = languagesKnown;
    }

    public List<String> getChatHistory() {
        return chatHistory;
    }

    public void setChatHistory(List<String> chatHistory) {
        this.chatHistory = chatHistory;
    }

    public void addChatHistory(String chatId) {
        chatHistory.add(chatId);
    }

    public Map toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("auth_id", authId);
        map.put("full_name", fullName);
        map.put("email", email);
        map.put("bio", bio);
        map.put("dob", dob.toString());
        map.put("created_at", createdAt.toString());
        map.put("languages_learning", languagesLearning);
        map.put("languages_known", languagesKnown);
        map.put("chat_history", chatHistory);
        return map;
    }
}
