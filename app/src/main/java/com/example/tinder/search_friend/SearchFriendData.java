package com.example.tinder.search_friend;

import android.util.Log;

import com.example.model.User;
import com.example.rest.RetrofitClient;
import com.example.rest.service.SearchFriendService;
import com.example.tinder.authentication.UserAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFriendData {

    private static SearchFriendData searchFriendData;

    private List<SearchFriendService.User> dataBuff;
    private boolean isLoading;

    private SearchFriendData() {
        dataBuff = new ArrayList<>();
        isLoading = false;
        getUsersFromServer();
    }

    public static SearchFriendData getInstance() {
        if (searchFriendData == null) {
            searchFriendData = new SearchFriendData();
        }
        return searchFriendData;
    }

    private void getUsersFromServer() {
        Log.d("token", UserAuth.getInstance().getUser().getAuthen_token());
        RetrofitClient.getSearchFriendService().getUsers("Barer " + UserAuth.getInstance().getUser().getAuthen_token())
                .enqueue(new Callback<List<SearchFriendService.User>>() {
            @Override
            public void onResponse(Call<List<SearchFriendService.User>> call, Response<List<SearchFriendService.User>> response) {
                if (response.body() != null) {
                    SearchFriendData.this.dataBuff = response.body();
                }
                SearchFriendData.this.isLoading = false;
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
            newUser = new User(this.dataBuff.get(0));
            Log.d("id", "id" + newUser.getId());
            this.dataBuff.remove(0);
        }
        if (this.isExhaustedBuff()) {
            this.loadData();
        }
        return newUser;
    }

    public boolean isBufferEmpty() {
        return this.dataBuff.size() < 1;
    }

    public void loadData() {
        if (this.isLoading) {
            return;
        }
        this.isLoading = true;
        if (isExhaustedBuff()){
            getUsersFromServer();
        }
    }

    public boolean isExhaustedBuff() {
        return this.dataBuff.size() < 5;
    }

}
