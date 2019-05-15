package com.example.rest.service;

import com.example.rest.model.MessageError;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UpdateUserService {

    @POST("/api/settings")
    Call<MessageError> updateUserInfo (
            @Header("Authorization") String authorization,
            @Field("name") String name,
            @Field("gender") String gender,
            @Field("age") int age,
            @Field("phone") String phone,
            @Field("description") String description,
            @Field("city") String city,
            @Field("workplace") String workplace);

    @POST("/api/settings")
    Call<MessageError> updateUserSettings (
            @Header("Authorization") String authorization,
            @Field("swipe_gender") String swipe_gender,
            @Field("min_age") int min_age,
            @Field("max_age") int max_age,
            @Field("max_distance") int max_distance);

}
