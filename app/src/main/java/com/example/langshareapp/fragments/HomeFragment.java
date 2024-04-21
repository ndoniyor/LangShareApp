package com.example.langshareapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.langshareapp.adapters.usercardadapter.UserCardAdapter;
import com.example.langshareapp.databinding.FragmentHomeBinding;
import com.example.langshareapp.viewmodels.HomeViewModel;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView feedRecyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new HomeViewModel();

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        feedRecyclerView = binding.feedRecyclerView;
        feedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        homeViewModel.getUserFeed().observe(getViewLifecycleOwner(), userFeed -> {
            feedRecyclerView.setAdapter(new UserCardAdapter(userFeed));
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}