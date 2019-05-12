package com.example.rest;


import android.net.Uri;

import com.example.rest.service.MessageService;
import com.example.rest.service.SearchFriendService;
import com.example.rest.service.SigninService;
import com.example.rest.service.SignupService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;
    public static final String BASE_URL = "http://167.99.69.92";

    private static Retrofit getNewInstance(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static SignupService getSignupService() {
        return getNewInstance(BASE_URL).create(SignupService.class);
    }

    public static SigninService getSigninService() {
        return getNewInstance(BASE_URL).create(SigninService.class);
    }

    public static MessageService getMessageService() {
        return getNewInstance(BASE_URL).create(MessageService.class);
    }

    public static SearchFriendService getSearchFriendService() {
        return getNewInstance(BASE_URL).create(SearchFriendService.class);
    }
}
