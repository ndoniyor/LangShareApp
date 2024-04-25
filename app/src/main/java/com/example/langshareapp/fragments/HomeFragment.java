package com.example.langshareapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.langshareapp.R;
import com.example.langshareapp.adapters.usercardadapter.UserCardAdapter;
import com.example.langshareapp.databinding.FragmentHomeBinding;
import com.example.langshareapp.viewmodels.HomeViewModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView feedRecyclerView;
    private UserCardAdapter userCardAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new HomeViewModel();

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        feedRecyclerView = binding.feedRecyclerView;
        feedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        userCardAdapter = new UserCardAdapter(new ArrayList<>());
        feedRecyclerView.setAdapter(userCardAdapter);

        homeViewModel.getUserFeed().observe(getViewLifecycleOwner(), userFeed -> {
            userCardAdapter.updateData(userFeed);
        });



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}