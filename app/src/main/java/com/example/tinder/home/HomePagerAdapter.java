package com.example.tinder.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.tinder.R;

import java.util.ArrayList;

import androidx.navigation.Navigation;


public class HomePagerAdapter extends PagerAdapter {

    private Context context;
    private int[] resList;

    public HomePagerAdapter(Context context, int[] resList) {
        this.context = context;
        this.resList = resList;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = getView(container, resList[position]);
        container.addView(view);
        return view;
    }

    private View getView(ViewGroup container, int res) {
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View retView = inflater.inflate(res, container, false);
        switch (res) {
            case R.layout.layout_tab_profile:
                setUpControlForProfile(retView);
                break;
            case R.layout.layout_tab_search_friend:
                break;
            case R.layout.layout_tab_message_box:
                setUpControlForMessageBox(retView);
                break;
                default:
                    break;
        }
        return retView;
    }

    /**
     *  set up controls for profile tab
     */
    private void setUpControlForProfile(View view) {
        ImageButton btnToUserInfo = view.findViewById(R.id.btnToUserInfo);
        btnToUserInfo.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_homeFragment_to_userInforFragment, null));
    }

    /**
     *  set up controls for message box tab
     */
    private void setUpControlForMessageBox(View view) {
        RecyclerView rvMatchList;
        RecyclerView rvMessageList;
        rvMatchList = view.findViewById(R.id.rvMatchList);
        rvMessageList = view.findViewById(R.id.rvMessageList);
        rvMatchList.setHasFixedSize(true);
        rvMessageList.setHasFixedSize(true);

        // use a linear layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvMatchList.setLayoutManager(layoutManager);

        layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvMessageList.setLayoutManager(layoutManager);
        rvMessageList.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));

        ArrayList<String> list = new ArrayList<String>();
        list.add("item 1");
        list.add("item 2");
        list.add("item 1");
        list.add("item 2");
        MatchListAdapter adapter = new MatchListAdapter(list);
        rvMatchList.setAdapter(adapter);

        MessageListAdapter mssAdapter = new MessageListAdapter(list);
        rvMessageList.setAdapter(mssAdapter);
    }

    @Override
    public int getCount() {
        return resList.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup collection, int position, @NonNull Object view) {
        collection.removeView((View) view);
    }

}
