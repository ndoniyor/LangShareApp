package com.example.langshareapp.adapters.usercardadapter;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.langshareapp.R;
import com.example.langshareapp.activities.ChatActivity;

import java.util.List;

public class UserCardViewHolder extends RecyclerView.ViewHolder {

    private TextView userInfo, userBio, userLanguagesLearning, userLanguagesKnown;
    private Button chatButton;

    public UserCardViewHolder(@NonNull View itemView) {
        super(itemView);
        userInfo = itemView.findViewById(R.id.user_info);
        userBio = itemView.findViewById(R.id.user_bio);
        userLanguagesLearning = itemView.findViewById(R.id.user_languages_learning);
        userLanguagesKnown = itemView.findViewById(R.id.user_languages_known);
        chatButton = itemView.findViewById(R.id.chat_user_button);
    }

    public void bind(String userInfo, String userBio, List<String> userLanguagesLearning, List<String> userLanguagesKnown, String senderId, String receiverId) {
        this.userInfo.setText(userInfo);
        this.userBio.setText(userBio);
        this.userLanguagesLearning.setText("Learning: " + String.join(", ", userLanguagesLearning));
        this.userLanguagesKnown.setText("Knows: " + String.join(", ", userLanguagesKnown));

        chatButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ChatActivity.class);
            intent.putExtra("senderId", senderId);
            intent.putExtra("receiverId", receiverId);
            intent.putExtra("chatRoomId", "");
            v.getContext().startActivity(intent);
        });
    }
}
