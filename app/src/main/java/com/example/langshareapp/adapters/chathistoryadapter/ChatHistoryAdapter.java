package com.example.langshareapp.adapters.chathistoryadapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.langshareapp.R;
import com.example.langshareapp.activities.ChatActivity;
import com.example.langshareapp.utils.ChatRoom;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChatHistoryAdapter extends RecyclerView.Adapter<ChatHistoryAdapter.ChatHistoryViewHolder> {
    private List<ChatRoom> chatRooms;
    private static String currentUserId;

    public ChatHistoryAdapter(List<ChatRoom> chatRooms, String currentUserId) {
        this.chatRooms = chatRooms;
        ChatHistoryAdapter.currentUserId = currentUserId;
    }

    @NonNull
    @Override
    public ChatHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_history_item, parent, false);
        return new ChatHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatHistoryViewHolder holder, int position) {
        ChatRoom chatRoom = chatRooms.get(position);
        holder.bind(chatRoom);
    }

    @Override
    public int getItemCount() {
        return chatRooms != null ? chatRooms.size() : 0;
    }

    public void setChatRooms(List<ChatRoom> newChatRooms) {
        if (this.chatRooms == null) {
            this.chatRooms = new ArrayList<>();
        }
        for (ChatRoom newChatRoom : newChatRooms) {
            int index = this.chatRooms.indexOf(newChatRoom);
            if (index != -1) {
                // If the chat room already exists, replace it with the new one
                this.chatRooms.set(index, newChatRoom);
            } else {
                // If the chat room doesn't exist, add it
                this.chatRooms.add(newChatRoom);
            }
        }
        notifyDataSetChanged();
    }

    public void setUserId(String userId) {
        ChatHistoryAdapter.currentUserId = userId;
    }

    static class ChatHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView userInfo;
        TextView latestMessage;
        LinearLayout chatHistoryItem;

        public ChatHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            userInfo = itemView.findViewById(R.id.user_info);
            latestMessage = itemView.findViewById(R.id.latest_message);
            chatHistoryItem = itemView.findViewById(R.id.chat_history_item);
        }

        public void bind(ChatRoom chatRoom) {
            int historySize = chatRoom.getMessages().size();
            for (String userId : chatRoom.getParticipants()) {
                if (!Objects.equals(userId, currentUserId)) {
                    userInfo.setText(userId);
                    break;
                }
            }
            latestMessage.setText(chatRoom.getMessages().get(String.valueOf(historySize - 1)).getContent());

            chatHistoryItem.setOnClickListener(v -> {
                String otherUserId = "";
                for (String userId : chatRoom.getParticipants()) {
                    if (!Objects.equals(userId, currentUserId)) {
                        otherUserId = userId;
                        break;
                    }
                }

                Intent intent = new Intent(v.getContext(), ChatActivity.class);
                intent.putExtra("senderId", currentUserId);
                intent.putExtra("receiverId", otherUserId);
                intent.putExtra("chatRoomId", chatRoom.getChatRoomId());
                v.getContext().startActivity(intent);
            });

        }
    }

    public void clearData(){
        if(chatRooms != null) {
            chatRooms.clear();
        }
        notifyDataSetChanged();
    }
}