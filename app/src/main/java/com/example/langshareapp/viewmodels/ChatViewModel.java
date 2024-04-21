package com.example.langshareapp.viewmodels;


import androidx.lifecycle.ViewModel;

import com.example.langshareapp.repositories.FirebaseRTDatabaseRepository;
import com.example.langshareapp.utils.Message;

public class ChatViewModel extends ViewModel{
    private FirebaseRTDatabaseRepository mRepository;

    public ChatViewModel(String senderId, String receiverId){
        mRepository = new FirebaseRTDatabaseRepository(senderId, receiverId);
        mRepository.createChatRoom();
    }

    public void sendMessage(String senderId, String receiverId, String content, long timestamp){
        Message message = new Message(senderId, receiverId, content, timestamp);
        mRepository.sendMessage(message);
    }

}
