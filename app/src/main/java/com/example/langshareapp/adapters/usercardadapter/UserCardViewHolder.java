package com.example.langshareapp.adapters.usercardadapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.langshareapp.R;

public class UserCardViewHolder extends RecyclerView.ViewHolder {

    private TextView userInfo, userBio, userLanguagesLearning, userLanguagesKnown;

    public UserCardViewHolder(@NonNull View itemView) {
        super(itemView);
        userInfo = itemView.findViewById(R.id.user_info);
        userBio = itemView.findViewById(R.id.user_bio);
        userLanguagesLearning = itemView.findViewById(R.id.user_languages_learning);
        userLanguagesKnown = itemView.findViewById(R.id.user_languages_known);
    }

    public void bind(String userInfo, String userBio, String userLanguagesLearning, String userLanguagesKnown) {
        this.userInfo.setText(userInfo);
        this.userBio.setText(userBio);
        this.userLanguagesLearning.setText(userLanguagesLearning);
        this.userLanguagesKnown.setText(userLanguagesKnown);
    }
}
