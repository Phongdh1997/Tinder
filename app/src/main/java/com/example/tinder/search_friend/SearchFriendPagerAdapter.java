package com.example.tinder.search_friend;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class SearchFriendPagerAdapter extends FragmentStatePagerAdapter {

    public final int NUM_PAGE = 4;

    public SearchFriendPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        return new SearchFriendItemFragment();
    }

    @Override
    public int getCount() {
        return NUM_PAGE;
    }
}
