package com.example.langshareapp.viewmodels;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.langshareapp.repositories.FirebaseFirestoreRepository;
import com.example.langshareapp.repositories.FirebaseRTDatabaseRepository;
import com.example.langshareapp.utils.LangShareUser;
import com.example.langshareapp.utils.Message;

import java.util.HashMap;
import java.util.List;

public class ChatViewModel extends ViewModel{
    private FirebaseRTDatabaseRepository mRepository;
    private FirebaseFirestoreRepository mFirestoreRepository;
    private MutableLiveData<HashMap<String, Message>> mMessages;
    private LangShareUser mUser;

    public ChatViewModel(String senderId, String receiverId, String chatRoomId){
        mRepository = new FirebaseRTDatabaseRepository(senderId, receiverId);
        mFirestoreRepository = FirebaseFirestoreRepository.getInstance();

        final String chatId;
        if(chatRoomId.isEmpty()){
            chatId = mRepository.createChatRoom();
        } else {
            chatId = chatRoomId;
            mRepository.connectToChatRoom(chatId);
        }

        mFirestoreRepository.getCurrentUser(
                user -> {
                    mUser = user;
                    mUser.addChatHistory(chatId);
                    mFirestoreRepository.addConversationToHistory(mUser.getAuthId(), chatId);
                }
        );

        mMessages = new MutableLiveData<>();

        mRepository.addChatRoomUpdateListener(chatRoom -> {
            mMessages.postValue(chatRoom.getMessages());
        });
    }

    public void sendMessage(String senderId, String receiverId, String content, long timestamp){
        int matchId = mMessages.getValue() != null ? mMessages.getValue().size() : 0;
        Message message = new Message(matchId, senderId, receiverId, content, timestamp);
        mRepository.sendMessage(message);
    }

    public LiveData<HashMap<String, Message>> getMessages(){
        return mMessages;
    }
}
