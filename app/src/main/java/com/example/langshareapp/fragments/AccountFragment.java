package com.example.langshareapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.langshareapp.R;
import com.example.langshareapp.activities.ChatActivity;
import com.example.langshareapp.databinding.FragmentAccountBinding;
import com.example.langshareapp.utils.LangShareUser;
import com.example.langshareapp.viewmodels.AccountViewModel;
import com.example.langshareapp.widgets.OptionMenu;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

public class AccountFragment extends Fragment {

    private FragmentAccountBinding binding;
    private LangShareUser user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        AccountViewModel accountViewModel = new AccountViewModel();

        binding = FragmentAccountBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        TextView textView = binding.helloText;

        accountViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
                this.user = user;
                textView.setText("Hello, " + user.getFullName() + "!");
            }
        );

        OptionMenu optionMenu = binding.optionMenu;
        optionMenu.editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.action_accountFragment_to_editProfileFragment);
            }
        });

        optionMenu.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance()
                        .signOut(getActivity())
                        .addOnCompleteListener(task -> {
                            NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment_activity_main);
                            navController.navigate(R.id.action_accountFragment_to_landingPage);
                        });
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}