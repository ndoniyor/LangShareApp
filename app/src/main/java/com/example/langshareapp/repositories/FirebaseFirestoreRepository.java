package com.example.langshareapp.repositories;

import android.util.Log;

import com.example.langshareapp.utils.LangShareUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FirebaseFirestoreRepository {
    private static FirebaseFirestoreRepository instance;
    private FirebaseFirestore db;
    private CollectionReference usersCollection;
    private LangShareUser currentUser;

    public interface UserRetrievedCallback {
        void onCallback(LangShareUser user);
    }

    public interface UserSavedCallback {
        void onCallback(boolean status);
    }

    private FirebaseFirestoreRepository() {
        db = FirebaseFirestore.getInstance();
        usersCollection = db.collection("users");
    }

    public static FirebaseFirestoreRepository getInstance() {
        if (instance == null) {
            instance = new FirebaseFirestoreRepository();
        }
        return instance;
    }

    public LangShareUser getCurrentUser() {
        return currentUser;
    }

    public void getUserByMAuthId(String mAuthId, UserRetrievedCallback callback) {
        usersCollection
                .whereEqualTo("auth_id", mAuthId)
                .get()
                .addOnCompleteListener(queryDocumentSnapshots -> {
                    List<DocumentSnapshot> res = queryDocumentSnapshots.getResult().getDocuments();
                    if (res.size() > 0)
                        callback.onCallback(LangShareUser.fromDocument(res.get(0)));
                    else
                        callback.onCallback(null);
                })
                .addOnFailureListener(e -> {
                    Log.e("FirestoreRepo", "Error getting user by mAuthId", e);
                    callback.onCallback(null);
                });

    }

    public void saveUser(LangShareUser user, UserSavedCallback callback) {
        try {
            usersCollection.add(user.toMap())
                    .addOnSuccessListener(documentReference -> {
                        Log.i("FirestoreRepo", "User added with ID: " + documentReference.getId());
                        callback.onCallback(true);
                    })
                    .addOnFailureListener(e -> {
                        Log.e("FirestoreRepo", "Error adding user", e);
                        callback.onCallback(false);
                    });
        } catch (Exception e) {
            Log.e("FirestoreRepo", "Error adding user", e);
        }
    }

}
