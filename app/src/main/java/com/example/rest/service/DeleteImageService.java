package com.example.rest.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DeleteImageService {
    @POST("/upload/delete_image")
    Call<ResponseBody> deleteImage(@Header("Authorization") String authorization, @Body Num num);

    class Num {

        public Num(Integer num) {
            this.num = num;
        }

        @SerializedName("num")
        @Expose

        private Integer num;

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }
    }

}
