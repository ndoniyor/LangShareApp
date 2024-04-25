package com.example.langshareapp.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.langshareapp.repositories.FirebaseFirestoreRepository;
import com.example.langshareapp.utils.LangShareUser;

public class AccountViewModel extends ViewModel {

    private FirebaseFirestoreRepository firebaseFirestoreRepository;
    private MutableLiveData<LangShareUser> user = new MutableLiveData<>();

    public AccountViewModel() {
        firebaseFirestoreRepository = FirebaseFirestoreRepository.getInstance();
        firebaseFirestoreRepository.getCurrentUser(user::setValue);
    }

    public LiveData<LangShareUser> getUser() {
        return user;
    }
}