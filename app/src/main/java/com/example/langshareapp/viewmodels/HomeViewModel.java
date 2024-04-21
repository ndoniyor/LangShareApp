package com.example.langshareapp.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.langshareapp.repositories.FirebaseFirestoreRepository;
import com.example.langshareapp.utils.LangShareUser;
import com.google.firebase.Firebase;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private FirebaseFirestoreRepository repository;
    private LangShareUser currentUser;
    private MutableLiveData<List<LangShareUser>> userFeed;

    public HomeViewModel() {
        Log.i("HomeViewModel", "Constructor called");
        repository = FirebaseFirestoreRepository.getInstance();
        repository.getCurrentUser(user -> {
            Log.i("HomeViewModel", "User retrieved: " + user.getFullName());
            repository.getUsers(user, users -> {
                Log.i("HomeViewModel", "Users retrieved: " + users.size());
                userFeed.postValue(users);
            });
        });
        userFeed = new MutableLiveData<>();
    }

    public LiveData<List<LangShareUser>> getUserFeed() {
        return userFeed;
    }

}