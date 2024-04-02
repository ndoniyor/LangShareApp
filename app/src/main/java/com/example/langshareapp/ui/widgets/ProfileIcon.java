package com.example.langshareapp.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.FrameLayout;

import com.example.langshareapp.R;

public class ProfileIcon extends FrameLayout {
    private ImageView profileImageView;
    public ProfileIcon(Context context) {
        super(context);
        init(context, null);
    }

    public ProfileIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ProfileIcon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater.from(context).inflate(R.layout.profile_icon, this, true);
        profileImageView = findViewById(R.id.profile_image_view);
    }

    public void setProfileImage(int imageResource) {
        profileImageView.setImageResource(imageResource);
    }

}
