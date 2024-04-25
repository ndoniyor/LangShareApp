package com.example.langshareapp.viewmodels;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.langshareapp.repositories.FirebaseFirestoreRepository;
import com.example.langshareapp.utils.LangShareUser;

public class EditProfileViewModel extends ViewModel {

    private FirebaseFirestoreRepository firebaseFirestoreRepository;
    private final MutableLiveData<LangShareUser> user = new MutableLiveData<>();

    public EditProfileViewModel() {
        firebaseFirestoreRepository = FirebaseFirestoreRepository.getInstance();
        firebaseFirestoreRepository.getCurrentUser(user::setValue);
    }

    public LiveData<LangShareUser> getUser() {
        return user;
    }

    public void updateUser(LangShareUser user, FirebaseFirestoreRepository.UserSavedCallback callback) {
        firebaseFirestoreRepository.updateUser(user , status -> {
            if (status) {
                this.user.setValue(user);
            }
        });
    }
}
