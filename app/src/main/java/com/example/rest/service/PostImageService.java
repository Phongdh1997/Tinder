package com.example.rest.service;

import com.example.tinder.authentication.UserAuth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PostImageService {
    @POST("/upload/upload_image")
    Call<String> upImage(@Field("image") String pathImage,@Field("num") Integer num, @Header("Authorization") String token);
}
