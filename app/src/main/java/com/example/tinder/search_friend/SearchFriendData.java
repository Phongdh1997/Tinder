package com.example.tinder.search_friend;

import android.util.Log;

import com.example.model.User;

import java.util.ArrayList;
import java.util.Random;

public class SearchFriendData {

    private static SearchFriendData searchFriendData;

    private ArrayList<User> dataBuff;
    private boolean isLoading;

    private SearchFriendData() {
        dataBuff = new ArrayList<>();
        for (int i = 1; i < 30; i++) {
            dataBuff.add(new User(i,i + "@gmail.com", "fds", "user " + i, i, "male"));
        }
        isLoading = false;
    }

    public static SearchFriendData getInstance() {
        if (searchFriendData == null) {
            searchFriendData = new SearchFriendData();
        }
        return searchFriendData;
    }

    /**
     *
     * @return: user item
     */
    public User getUserData () {
        User currUser = new User();

        // set first item to view and remove it from buffer
        if (!this.isBufferEmpty()) {
            currUser = this.dataBuff.get(0);
            Log.d("id", "id" + currUser.getId());
            this.dataBuff.remove(0);
        }
        if (this.isExhaustedBuff()) {
            this.loadData();
        }
        return currUser;
    }

    public boolean isBufferEmpty() {
        return this.dataBuff.size() < 1;
    }

    public void loadData() {
        if (this.isLoading) {
            return;
        }
        this.isLoading = true;
        Log.d("Load", "new item");

        // load new data from server
        Random r = new Random();
        String item = "";
        for (int i = 0; i < 20; i++) {
            dataBuff.add(new User(r.nextInt(), r.nextInt() + "@gmail.com", "fds", "user " + r.nextInt(), r.nextInt(), "male"));
            Log.d("item", "new item");
        }

        // update isLoading = false when load data success
        this.isLoading = false;
    }

    public boolean isExhaustedBuff() {
        return this.dataBuff.size() < 20;
    }

    public ArrayList<User> getDataBuff() {
        return dataBuff;
    }

    public boolean isLoading() {
        return isLoading;
    }

}
