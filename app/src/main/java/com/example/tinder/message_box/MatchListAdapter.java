package com.example.tinder.message_box;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tinder.R;

import java.util.ArrayList;

public class MatchListAdapter extends RecyclerView.Adapter<MatchListAdapter.MatchViewHolder> {

    private ArrayList<String> datas;

    public MatchListAdapter(ArrayList<String> list) {
        this.datas = list;
    }

    public static class  MatchViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public MatchViewHolder(View view) {
            super(view);
        }
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.match_item, viewGroup, false);
        return new MatchViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder matchViewHolder, int i) {
    }

    @Override
    public int getItemCount() {
        return this.datas.size();
    }

}
