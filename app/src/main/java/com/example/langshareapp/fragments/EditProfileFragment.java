package com.example.langshareapp.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.langshareapp.databinding.FragmentEditProfileBinding;
import com.example.langshareapp.utils.LangShareUser;
import com.example.langshareapp.viewmodels.EditProfileViewModel;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.List;

public class EditProfileFragment extends Fragment {
    private FragmentEditProfileBinding binding;
    private LangShareUser user;
    private MultiAutoCompleteTextView languagesLearningEditText;
    private MultiAutoCompleteTextView languagesKnownEditText;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        EditProfileViewModel editProfileViewModel = new EditProfileViewModel();

        binding = FragmentEditProfileBinding.inflate(inflater, container, false);
        languagesLearningEditText = binding.languageLearningInput;
        languagesKnownEditText = binding.languageKnownInput;

        binding.saveProfileButton.setEnabled(false);

        setUpLanguageSelectors();
        setUpTextWatchers();

        editProfileViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
                this.user = user;
                binding.editNameText.setText(user.getFullName());
                binding.editBioText.setText(user.getBio());
                binding.languageLearningInput.setText(String.join(", ", user.getLanguagesLearning()));
                binding.languageKnownInput.setText(String.join(", ", user.getLanguagesKnown()));
                binding.saveProfileButton.setEnabled(true);
            }
        );


        binding.saveProfileButton.setOnClickListener(v -> {
            String learningInput = binding.languageLearningInput.getText().toString();
            List<String> languagesLearning = Arrays.asList(learningInput.split("\\s*,\\s*"));

            String knownInput = binding.languageKnownInput.getText().toString();
            List<String> languagesKnown = Arrays.asList(knownInput.split("\\s*,\\s*"));

            user.setFullName(binding.editNameText.getText().toString());
            user.setBio(binding.editBioText.getText().toString());
            user.setLanguagesLearning(languagesLearning);
            user.setLanguagesKnown(languagesKnown);

            editProfileViewModel.updateUser(user, status -> {
                if (status) {
                    Toast.makeText(getContext(), "Profile updated", Toast.LENGTH_SHORT).show();
                }
            });
        });

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void setUpTextWatchers() {
        languagesLearningEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    ((TextInputLayout) languagesLearningEditText.getParent().getParent()).setHint(null);
                } else {
                    ((TextInputLayout) languagesLearningEditText.getParent().getParent()).setHint("Language Learning");
                }
            }
        });

        languagesKnownEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    ((TextInputLayout) languagesKnownEditText.getParent().getParent()).setHint(null);
                } else {
                    ((TextInputLayout) languagesKnownEditText.getParent().getParent()).setHint("Language Learning");
                }
            }
        });
    }

    private void setUpLanguageSelectors() {
        String[] languages = new String[] { "English", "Uzbek", "Russian", "Spanish", "French", "German", "Chinese",
                "Japanese" };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, languages);

        languagesLearningEditText.setAdapter(adapter);
        languagesLearningEditText.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        languagesKnownEditText.setAdapter(adapter);
        languagesKnownEditText.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }
}
