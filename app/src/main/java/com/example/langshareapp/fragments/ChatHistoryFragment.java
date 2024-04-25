package com.example.langshareapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.langshareapp.R;
import com.example.langshareapp.adapters.chathistoryadapter.ChatHistoryAdapter;
import com.example.langshareapp.viewmodels.ChatHistoryViewModel;

import java.util.ArrayList;

public class ChatHistoryFragment extends Fragment {
    private ChatHistoryViewModel viewModel;
    private ChatHistoryAdapter adapter;

    public ChatHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ChatHistoryViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_history, container, false);
    
        RecyclerView recyclerView = view.findViewById(R.id.chat_history_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ChatHistoryAdapter(new ArrayList<>(), null);
        recyclerView.setAdapter(adapter);
    
        viewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            adapter.setUserId(user.getAuthId());
            adapter.notifyDataSetChanged();
    
            viewModel.getChatRooms(user).observe(getViewLifecycleOwner(), chatRooms -> {
                adapter.setChatRooms(chatRooms);
                adapter.notifyDataSetChanged();
            });
        });
    
        return view;
    }
}