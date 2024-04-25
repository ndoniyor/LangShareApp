package com.example.langshareapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.langshareapp.repositories.FirebaseFirestoreRepository;
import com.example.langshareapp.repositories.FirebaseRTDatabaseRepository;
import com.example.langshareapp.utils.ChatRoom;
import com.example.langshareapp.utils.LangShareUser;

import java.util.List;

public class ChatHistoryViewModel extends ViewModel {
    private MutableLiveData<List<ChatRoom>> chatRooms = new MutableLiveData<>();
    private FirebaseRTDatabaseRepository rtDatabaseRepository;
    private FirebaseFirestoreRepository firestoreRepository;
    private MutableLiveData<LangShareUser> user = new MutableLiveData<>();

    public ChatHistoryViewModel() {
        rtDatabaseRepository = new FirebaseRTDatabaseRepository();
        firestoreRepository = FirebaseFirestoreRepository.getInstance();
        firestoreRepository.getCurrentUser(res -> {
            user.setValue(res);
        });
    }

    public LiveData<List<ChatRoom>> getChatRooms(LangShareUser user) {
        return rtDatabaseRepository.getChatRooms(user);
    }

    public LiveData<LangShareUser> getUser() {
        return user;
    }

    public void clearChatRooms() {
        chatRooms.setValue(null);
    }
}