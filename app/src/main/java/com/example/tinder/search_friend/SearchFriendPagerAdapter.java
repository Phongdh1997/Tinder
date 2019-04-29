package com.example.tinder.search_friend;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.model.User;
import com.example.tinder.R;

import java.util.ArrayList;
import java.util.Random;

import androidx.navigation.Navigation;

public class SearchFriendPagerAdapter extends PagerAdapter {

    public static final int PAGE_NUM = 2000;

    private Context context;
    private ArrayList<User> dataBuff;
    private boolean isLoading;

    public SearchFriendPagerAdapter(Context context) {
        this.context = context;
        this.isLoading = false;
        dataBuff = new ArrayList<>();
        for (int i = 1; i < 30; i++) {
            dataBuff.add(new User(i + "@gmail.com", "fds", "user " + i, i, "male"));
        }
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_search_friend_item, container, false);

        // add controls
        TextView txtName = view.findViewById(R.id.txtName);
        ImageButton btnDetailInfo = view.findViewById(R.id.btnDetailInfo);

        User currUser = null;

        // set first item to view and remove it from buffer
        if (!this.isBufferEmpty()) {
            currUser = this.dataBuff.get(0);
            txtName.setText(currUser.getName());
            this.dataBuff.remove(0);
        }

        // add event
        Bundle user = new Bundle();
        if (currUser != null) {
            user.putInt("id", currUser.getId());
            user.putString("authen_token", currUser.getAuthen_token());
            user.putString("phone", currUser.getPhone());
            user.putString("mail", currUser.getMail());
            user.putString("password", currUser.getPassword());
            user.putString("name", currUser.getName());
            user.putString("decription", currUser.getDecription());
            user.putString("gender", currUser.getGender());
            user.putInt("age", currUser.getAge());
        }
        btnDetailInfo.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_userInforFragment, user));

        if (this.isExhaustedBuff()) {
            this.loadData();
        }

        // add view to container
        container.addView(view);
        return view;
    }

    private boolean isExhaustedBuff() {
        return this.dataBuff.size() < 20;
    }

    public boolean isBufferEmpty() {
        return this.dataBuff.size() < 1;
    }

    private void loadData() {
        if (this.isLoading) {
            return;
        }
        this.isLoading = true;
        Log.d("Load", "new item");

        // load new data from server
        Random r = new Random();
        String item = "";
        for (int i = 0; i < 20; i++) {
            dataBuff.add(new User(r.nextInt() + "@gmail.com", "fds", "user " + r.nextInt(), r.nextInt(), "male"));
            Log.d("item", "new item");
        }

        // update isLoading = false when load data success
        this.isLoading = false;
    }

    @Override
    public int getCount() {
        return PAGE_NUM;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup collection, int position, @NonNull Object view) {
        Log.d("remove", "item removed");
        collection.removeView((View) view);
    }
}