package com.example.rest;

import com.example.rest.service.SignupService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;
    public static final String BASE_URL = "http://167.99.69.92";

    private static Retrofit getNewInstance(String baseUrl) {
        if (retrofit==null) {
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
}
