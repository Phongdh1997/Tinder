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
    private boolean isLoading;
    private boolean isOutOfData;

    private SearchFriendData() {
        dataBuff = new ArrayList<>();
        isLoading = false;
        isOutOfData = false;
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
                    Log.d("lise size", " = " + response.body().size());
                    if (response.body().size() < 6) {
                        SearchFriendData.this.isOutOfData = true;
                    }
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
        } else {
            newUser = new User();
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
        if (this.isLoading || this.isOutOfData) {
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
