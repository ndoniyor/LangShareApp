package com.example.langshareapp.repositories;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.langshareapp.utils.ChatRoom;
import com.example.langshareapp.utils.LangShareUser;
import com.example.langshareapp.utils.Message;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

public class FirebaseRTDatabaseRepository {
    private final DatabaseReference mDatabase;
    private DatabaseReference mChatRef;
    private ChatRoom chatRoom;

    public FirebaseRTDatabaseRepository(String senderId, String receiverId) {
        this.mDatabase = FirebaseDatabase.getInstance().getReference("chat_app/Chats");
        this.chatRoom = new ChatRoom(UUID.randomUUID().toString(), senderId , receiverId);
    }

    public void createChatRoom() {
        mChatRef = mDatabase.child(chatRoom.getChatRoomId());
        mChatRef.setValue(chatRoom);
    }

    public void connectToChatRoom(String chatRoomId) {
        mChatRef = mDatabase.child(chatRoomId);
    }

    public void sendMessage(Message message) {
        chatRoom.addMessage(message);
        mChatRef.setValue(chatRoom);
    }

    public void addChatRoomUpdateListener(ChatRoomUpdateCallback callback) {
        mChatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatRoom = dataSnapshot.getValue(ChatRoom.class);
                callback.onChatRoomUpdated(chatRoom);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseRTDatabaseRepo", "Failed to read value.", databaseError.toException());
            }
        });
    }


    public interface ChatRoomUpdateCallback {
        void onChatRoomUpdated(ChatRoom chatRoom);
    }
}
