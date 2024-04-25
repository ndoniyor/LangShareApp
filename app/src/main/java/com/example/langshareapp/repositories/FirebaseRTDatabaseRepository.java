package com.example.langshareapp.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.langshareapp.utils.ChatRoom;
import com.example.langshareapp.utils.LangShareUser;
import com.example.langshareapp.utils.Message;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class FirebaseRTDatabaseRepository {
    private final DatabaseReference mDatabase;
    private DatabaseReference mChatRef;
    private ChatRoom chatRoom;


    public FirebaseRTDatabaseRepository() {
        this.mDatabase = FirebaseDatabase.getInstance().getReference("chat_app/Chats");
    }
    public FirebaseRTDatabaseRepository(String senderId, String receiverId) {
        this.mDatabase = FirebaseDatabase.getInstance().getReference("chat_app/Chats");
        this.chatRoom = new ChatRoom(UUID.randomUUID().toString());
        this.chatRoom.addUser(receiverId);
        this.chatRoom.addUser(senderId);
    }

    public String createChatRoom() {
        Log.i("FirebaseRTDatabaseRepo", "Creating chat room, chatRoomId: " + chatRoom.getChatRoomId());
        mChatRef = mDatabase.child(chatRoom.getChatRoomId());
        mChatRef.setValue(chatRoom);
        return chatRoom.getChatRoomId();
    }

    public void connectToChatRoom(String chatRoomId) {
        mChatRef = mDatabase.child(chatRoomId);
    }

    public void sendMessage(Message message) {
        chatRoom.addMessage(message);
        mChatRef.child("messages").setValue(chatRoom.getMessages());
    }

    public HashMap<String, Message> getMessages() {
        return chatRoom.getMessages();
    }

    public void addChatRoomUpdateListener(ChatRoomUpdateCallback callback) {
        mChatRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Message> messageList = dataSnapshot.child("messages").getValue(new GenericTypeIndicator<List<Message>>() {
                });
                HashMap<String, Message> messageMap = new HashMap<>();
                if (messageList != null) {
                    for (int i = 0; i < messageList.size(); i++) {
                        messageMap.put(String.valueOf(i), messageList.get(i));
                    }
                }
                chatRoom.setMessages(messageMap);
                callback.onChatRoomUpdated(chatRoom);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseRTDatabaseRepo", "Failed to read value.", databaseError.toException());
            }
        });
    }

    public LiveData<List<ChatRoom>> getChatRooms(LangShareUser user) {
        final MutableLiveData<List<ChatRoom>> liveData = new MutableLiveData<>();
        List<String> chatRoomIds = user.getChatHistory();
        final Set<ChatRoom> chatRooms = new HashSet<>();

        for (String chatRoomId : chatRoomIds) {
            Log.i("FirebaseRTDatabaseRepo", "Getting chat room: " + chatRoomId);
            Query query = mDatabase.child(chatRoomId);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.i("FirebaseRTDatabaseRepo", "Chat room found: " + dataSnapshot.getKey());
                    Log.i("FirebaseRTDatabaseRepo", "Chat room data: " + dataSnapshot.getValue());

                    if(!dataSnapshot.exists()){
                        return;
                    }

                    ChatRoom chatRoom = new ChatRoom();
                    chatRoom.setChatRoomId(dataSnapshot.child("chatRoomId").getValue(String.class));
                    chatRoom.setLastMessageId(dataSnapshot.child("lastMessageId").getValue(Integer.class));

                    List<String> participants = new ArrayList<>();
                    for (DataSnapshot participantSnapshot : dataSnapshot.child("participants").getChildren()) {
                        participants.add(participantSnapshot.getValue(String.class));
                    }
                    chatRoom.setParticipants(participants);

                    HashMap<String, Message> messages = new HashMap<>();
                    for (DataSnapshot messageSnapshot : dataSnapshot.child("messages").getChildren()) {
                        Message message = messageSnapshot.getValue(Message.class);
                        messages.put(messageSnapshot.getKey(), message);
                    }
                    chatRoom.setMessages(messages);

                    chatRooms.add(chatRoom);
                    liveData.setValue(new ArrayList<>(chatRooms)); // Set the value of LiveData when data changes
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("FirebaseRTDatabaseRepo", "Error getting chat room: ", databaseError.toException());
                }
            });
        }

        return liveData;
    }

    public interface ChatRoomUpdateCallback {
        void onChatRoomUpdated(ChatRoom chatRoom);
    }
}
