package com.example.langshareapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.langshareapp.MainActivity;
import com.example.langshareapp.R;
import com.example.langshareapp.viewmodels.SignUpViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SignUpActivity extends AppCompatActivity {
    private TextInputEditText fullNameEditText;
    private TextInputEditText dobEditText;
    private TextInputEditText bioEditText;
    private MultiAutoCompleteTextView languagesLearningEditText;
    private MultiAutoCompleteTextView languagesKnownEditText;
    private SignUpViewModel signUpViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("SignUpActivity", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        signUpViewModel = new SignUpViewModel();

        fullNameEditText = findViewById(R.id.edit_name_text);
        dobEditText = findViewById(R.id.edit_dob_text);
        bioEditText = findViewById(R.id.edit_bio_text);
        languagesLearningEditText = findViewById(R.id.language_learning_input);
        languagesKnownEditText = findViewById(R.id.language_known_input);

        setUpLanguageSelectors();
        setUpTextWatchers();

        signUpViewModel.getSaveUserStatus().observe(this, status -> {
            if (Boolean.TRUE.equals(status)) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.save_profile_button).setOnClickListener(v -> {
            String[] split = languagesLearningEditText.getText().toString().split(",");
            List<String> languagesLearning = new ArrayList<>();
            for (String language : split) {
                String trimmedLanguage = language.trim();
                if (!trimmedLanguage.isEmpty()) {
                    languagesLearning.add(trimmedLanguage);
                }
            }

            split = languagesKnownEditText.getText().toString().split(",");
            List<String> languagesKnown = new ArrayList<>();
            for (String language : split) {
                String trimmedLanguage = language.trim();
                if (!trimmedLanguage.isEmpty()) {
                    languagesKnown.add(trimmedLanguage);
                }
            }

            signUpViewModel.saveUser(
                    fullNameEditText.getText().toString(),
                    dobEditText.getText().toString(),
                    bioEditText.getText().toString(),
                    languagesLearning,
                    languagesKnown);
        });
    }

    @Override
    public void onStart() {
        super.onStart();
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

        dobEditText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(10) });
        dobEditText.addTextChangedListener(new TextWatcher() {
            private boolean isDeleting;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                isDeleting = count > after;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!isDeleting) {
                    if (s.length() == 2 || s.length() == 5) {
                        dobEditText.setText(s + "/");
                        dobEditText.setSelection(s.length() + 1);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void setUpLanguageSelectors() {
        String[] languages = new String[] { "English", "Uzbek", "Russian", "Spanish", "French", "German", "Chinese",
                "Japanese" };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, languages);

        languagesLearningEditText.setAdapter(adapter);
        languagesLearningEditText.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        languagesKnownEditText.setAdapter(adapter);
        languagesKnownEditText.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }
}
