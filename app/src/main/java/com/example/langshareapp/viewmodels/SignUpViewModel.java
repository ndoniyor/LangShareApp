package com.example.langshareapp.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.langshareapp.repositories.FirebaseAuthRepository;
import com.example.langshareapp.repositories.FirebaseFirestoreRepository;
import com.example.langshareapp.utils.LangShareUser;
import com.google.firebase.auth.FirebaseUser;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class SignUpViewModel extends ViewModel {
    private MutableLiveData<Boolean> saveUserStatus = new MutableLiveData<>();
    public LiveData<Boolean> getSaveUserStatus() {
        return saveUserStatus;
}

    private FirebaseFirestoreRepository firestoreRepository;
    private FirebaseAuthRepository authRepository;
    private FirebaseUser user;


    public SignUpViewModel() {
        firestoreRepository = FirebaseFirestoreRepository.getInstance();
        authRepository = FirebaseAuthRepository.getInstance();
        user = authRepository.getCurrentUser();
    }

    public void saveUser(String fullName, String dob, String bio, List<String> languagesLearning, List<String> languagesKnown) {
        Log.i("SignUpViewModel", String.format("saveUser: %s, %s, %s, %s, %s", fullName, dob, bio, languagesLearning, languagesKnown));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate parsedDob = LocalDate.parse(dob, formatter);
        LangShareUser newUser = new LangShareUser(user.getUid(), fullName, user.getEmail(), bio, parsedDob, LocalDate.now(), languagesLearning, languagesKnown);

        firestoreRepository.saveUser(newUser, status -> saveUserStatus.postValue(status));
    }

}
