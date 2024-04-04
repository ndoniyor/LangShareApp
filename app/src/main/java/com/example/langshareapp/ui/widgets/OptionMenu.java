package com.example.langshareapp.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.langshareapp.R;

public class OptionMenu extends LinearLayout {
    public Button editProfileButton;
    public Button logoutButton;
    
    public OptionMenu(Context context) {
        super(context);
        init(context, null);
    }

    public OptionMenu(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }
    
    public OptionMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.option_menu, this, true);

        editProfileButton = findViewById(R.id.edit_profile_button);
        logoutButton = findViewById(R.id.logout_button);
    }
}
