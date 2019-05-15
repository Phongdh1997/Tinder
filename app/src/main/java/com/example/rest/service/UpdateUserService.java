package com.example.rest.service;

import com.example.rest.model.MessageError;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UpdateUserService {

    @POST("/api/settings")
    Call<MessageError> updateUserInfo (
            @Header("Authorization") String authorization,
            @Query("name") String name,
            @Query("gender") String gender,
            @Query("age") int age,
            @Query("phone") String phone,
            @Query("description") String description,
            @Query("city") String city,
            @Query("workplace") String workplace);

    // khong day du
    @POST("/api/settings")
    Call<MessageError> updateUserInfo (
            @Header("Authorization") String authorization,
            @Query("name") String name,
            @Query("gender") String gender,
            @Query("age") int age,
            @Query("phone") String phone,
            @Query("description") String description);

    @POST("/api/settings")
    Call<MessageError> updateUserSettings (
            @Header("Authorization") String authorization,
            @Query("swipe_gender") String swipe_gender,
            @Query("min_age") int min_age,
            @Query("max_age") int max_age,
            @Query("max_distance") int max_distance);

}
