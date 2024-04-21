package com.example.langshareapp.adapters.usercardadapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.langshareapp.R;
import com.example.langshareapp.utils.LangShareUser;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public class UserCardAdapter extends RecyclerView.Adapter<UserCardViewHolder>{
    private List<LangShareUser> userFeed;

    public UserCardAdapter(List<LangShareUser> userFeed) {
        this.userFeed = userFeed;
    }

    @NonNull
    @Override
    public UserCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card, parent, false);
        return new UserCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserCardViewHolder holder, int position) {
        Log.i("UserCardAdapter", "onBindViewHolder called");
        LangShareUser user = userFeed.get(position);
        String name = user.getFullName();
        int age = Period.between(user.getDob(), LocalDate.now()).getYears();
        String bio = user.getBio();
        String languagesLearning = user.getLanguagesLearning().toString();
        String languagesKnown = user.getLanguagesKnown().toString();
        holder.bind(name + ", " + age, bio, languagesLearning, languagesKnown);
    }

    @Override
    public int getItemCount() {
        return userFeed != null ? userFeed.size() : 0;
    }
}
