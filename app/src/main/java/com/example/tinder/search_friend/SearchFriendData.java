package com.example.tinder.search_friend;

import android.util.Log;

import com.example.model.User;
import com.example.rest.RetrofitClient;
import com.example.rest.service.SearchFriendService;
import com.example.tinder.authentication.UserAuth;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFriendData {

    private static SearchFriendData searchFriendData;

    private List<SearchFriendService.User> dataBuff;
    private int index;
    private boolean isLoading;
    private boolean isOutOfData;

    private List<OnDataLoadDoneListener> listeners;

    public void addOnDataLoadDoneListener (OnDataLoadDoneListener listener) {
        this.listeners.add(listener);
    }

    public boolean removeDataLoadDondListener(OnDataLoadDoneListener listener) {
        Log.d("listener size", "size: " + listeners.size());
        return listeners.remove(listener);
    }

    public int getIndex() {
        return index;
    }

    public void back(int step) {
        index -= step;
    }

    private SearchFriendData() {
        dataBuff = new ArrayList<>();
        isLoading = false;
        isOutOfData = false;
        listeners = new ArrayList<>();
        index = 0;
    }

    public static SearchFriendData getInstance() {
        if (searchFriendData == null) {
            searchFriendData = new SearchFriendData();
        }
        return searchFriendData;
    }

    public void notifyDataSetChange() {
        for (OnDataLoadDoneListener listener : listeners) {
            listener.onLoadDone();
        }
    }

    private void getUsersFromServer() {
        Log.d("token", UserAuth.getInstance().getUser().getAuthen_token());
        Log.d("loading", "load more swipe list");
        RetrofitClient.getSearchFriendService().getUsers(UserAuth.getInstance().getUser().getHeaderAuthenToken())
                .enqueue(new Callback<List<SearchFriendService.User>>() {
            @Override
            public void onResponse(Call<List<SearchFriendService.User>> call, Response<List<SearchFriendService.User>> response) {
                if (response.body() != null) {
                    SearchFriendData.this.dataBuff = response.body();
                    SearchFriendData.this.isLoading = false;
                    SearchFriendData.this.index = 0;
                    Log.d("lise size", " = " + response.body().size());
                    if (response.body().size() < 6) {
                        SearchFriendData.this.isOutOfData = true;
                    }
                    notifyDataSetChange();
                }
                Log.d("get Search Friend", "code: " + response.code());
            }

            @Override
            public void onFailure(Call<List<SearchFriendService.User>> call, Throwable t) {
                t.printStackTrace();
                SearchFriendData.this.isLoading = false;
            }
        });
    }

    /**
     *
     * @return: user item
     */
    public User getUserData () {
        User newUser = null;

        // set first item to view and remove it from buffer
        if (!this.isBufferEmpty()) {
            newUser = new User(this.dataBuff.get(index));
            Log.d("get User data", "id" + newUser.getId() + "; index = " + index);
            index++;
        }
        if (this.isExhaustedBuff()) {
            this.loadData();
        }
        return newUser;
    }

    public boolean isBufferEmpty() {
        return index >= this.dataBuff.size();
    }

    public void loadData() {
        if (this.isLoading || this.isOutOfData) {
            return;
        }
        this.isLoading = true;
        getUsersFromServer();
    }

    public boolean isExhaustedBuff() {
        return this.dataBuff.size() - index < 5;
    }

    interface OnDataLoadDoneListener {
        void onLoadDone();
    }

}
