package com.example.tinder.profile;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tinder.R;

import java.util.ArrayList;
import java.util.Random;

import androidx.navigation.Navigation;

public class IntroducePagerAdapter extends PagerAdapter {

    public static final int NUM_PAGE = 4;

    private Context context;

    public IntroducePagerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.introduce_slider_item, container, false);

        // add view to container
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return NUM_PAGE;
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
