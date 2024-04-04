package com.example.langshareapp.ui.account.editProfile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.lifecycle.ViewModelProvider;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.langshareapp.databinding.FragmentEditProfileBinding;

public class EditProfileFragment extends Fragment {
    private FragmentEditProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EditProfileViewModel editProfileViewModel =
                new ViewModelProvider(this).get(EditProfileViewModel.class);

        binding = FragmentEditProfileBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
