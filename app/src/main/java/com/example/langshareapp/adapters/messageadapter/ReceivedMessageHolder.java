package com.example.langshareapp.adapters.messageadapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.langshareapp.R;
import com.example.langshareapp.utils.Message;

public class ReceivedMessageHolder extends RecyclerView.ViewHolder {
    private TextView messageText, timeText;

    public ReceivedMessageHolder(View itemView) {
        super(itemView);
        messageText = itemView.findViewById(R.id.text_message_body);
        timeText = itemView.findViewById(R.id.text_message_time);
    }

    public void bind(Message message) {
        messageText.setText(message.getContent());
        timeText.setText(message.getDateString());
    }
}
