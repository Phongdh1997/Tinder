package com.example.rest.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface PostImageService {
    @FormUrlEncoded
    @POST("/upload/upload_image")
    Call<ResponseMessage> upImage(@Field("image") String pathImage,@Field("num") Integer num, @Header("Authorization") String token);

    class ResponseMessage {
        @SerializedName("message")
        @Expose
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
