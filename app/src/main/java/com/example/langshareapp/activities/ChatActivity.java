package com.example.langshareapp.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.langshareapp.R;
import com.example.langshareapp.adapters.messageadapter.MessageAdapter;
import com.example.langshareapp.utils.Message;
import com.example.langshareapp.viewmodels.ChatViewModel;

import java.util.ArrayList;
import java.util.HashMap;

public class ChatActivity extends AppCompatActivity {
    private EditText editTextMessage;
    private Button sendButton;
    private RecyclerView recyclerView;
    private HashMap<String, Message> messageList;
    private ChatViewModel chatViewModel;
    private MessageAdapter messageAdapter;
    private String senderId;
    private String receiverId;
    private String chatId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        senderId = getIntent().getStringExtra("senderId");
        receiverId = getIntent().getStringExtra("receiverId");
        chatId = getIntent().getStringExtra("chatRoomId");

        editTextMessage = findViewById(R.id.edit_text_message);
        sendButton = findViewById(R.id.send_message_button);
        recyclerView = findViewById(R.id.chat_recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageAdapter = new MessageAdapter(new ArrayList<>(), senderId);
        recyclerView.setAdapter(messageAdapter);

        chatViewModel = new ChatViewModel(senderId, receiverId, chatId);

        chatViewModel.getMessages().observe(this, newMessages -> {
            if (newMessages != null) {
                messageAdapter.updateMessages(newMessages);
                recyclerView.scrollToPosition(messageAdapter.getItemCount() - 1);
            }
        });

        sendButton.setOnClickListener(v -> sendMessage());
    }

    private void sendMessage() {
        String messageText = editTextMessage.getText().toString().trim();
        if (!messageText.isEmpty()) {
            long timestamp = System.currentTimeMillis();
            chatViewModel.sendMessage(senderId, receiverId, messageText, timestamp);
            editTextMessage.setText("");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
