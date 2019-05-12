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
    private SearchFriendData searchFriendData;

    public SearchFriendPagerAdapter(Context context) {
        this.context = context;
        searchFriendData = SearchFriendData.getInstance();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        User currUser = searchFriendData.getUserData();
        FriendView view = new FriendView(this.context, currUser);
        searchFriendData.addOnDataLoadDoneListener(view);

        // add view to container
        view.setTag(position);
        container.addView(view);
        return view;
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
        if (view instanceof SearchFriendData.OnDataLoadDoneListener) {
            searchFriendData.removeDataLoadDondListener((SearchFriendData.OnDataLoadDoneListener) view);
        }
        collection.removeView((View) view);
    }
}