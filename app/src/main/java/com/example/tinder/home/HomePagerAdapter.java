package com.example.tinder.home;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;


public class HomePagerAdapter extends FragmentStatePagerAdapter {

    private Fragment [] listFragment;
    private String [] listTitle;

    public HomePagerAdapter(FragmentManager fm, Fragment [] listFragment, String [] listTitle) {
        super(fm);
        this.listFragment = listFragment;
        this.listTitle = listTitle;
    }

    @Override
    public Fragment getItem(int i) {
        return this.listFragment[i];
    }

    @Override
    public int getCount() {
        return listFragment != null ? listFragment.length : 0;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return listTitle != null ? listTitle[position] : "";
    }
}